package com.vs.auth.controller;


import com.vs.auth.model.MongoConnection;
import com.vs.auth.repository.AUserRepository;
import com.vs.auth.repository.UserSocialConnectionRepository;
import com.vs.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * Created by magnus on 2014-08-24.
 */
@Component
@Slf4j
public class SocialControllerUtil {


    private static final String USER_CONNECTION = "MY_USER_CONNECTION";
    private static final String USER_PROFILE = "MY_USER_PROFILE";

    @Autowired
    AUserRepository usersDao;


    @Autowired
    UserSocialConnectionRepository userSocialConnectionRepository;

    public void setModel(HttpServletRequest request, Principal currentUser, Model model) {

        // SecurityContext ctx = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");

        String userId =((currentUser == null) ? null : currentUser.getName());
        String path = request.getRequestURI();
        HttpSession session = request.getSession();

        MongoConnection connection = null;
        User profile = null;
        String displayName = null;
        String data = null;

        // Collect info if the user is logged in, i.e. userId is set
        if (userId != null) {

            // Get the current UserSocialConnection from the http session
            connection = getUserConnection(session, userId);

            // Get the current UserProfile from the http session
            profile = getUserProfile(session, userId);

            // Compile the best display name from the connection and the profile
            displayName = "Gopi";//getDisplayName(connection, profile);

            // Get user data from persistence storage
            data = "GOPI DATA TEST";//dataDao.getData(userId);
        }

        Throwable exception = (Throwable)session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        // Update the model with the information we collected
        model.addAttribute("exception",              exception == null ? null : exception.getMessage());
        model.addAttribute("currentUserId",          userId);
        model.addAttribute("currentUserProfile",     profile);
        model.addAttribute("currentUserConnection",  connection);
        model.addAttribute("currentUserDisplayName", displayName);
        model.addAttribute("currentData",            data);

            logInfo(request, model, userId, path, session);
    }

    protected void logInfo(HttpServletRequest request, Model model, String userId, String path, HttpSession session) {
        // Log the content of the model
        log.info("Path: " + path + ", currentUserId: " + userId);

        log.info("Non-null request-attributes:");
        for (Enumeration<String> rane = request.getAttributeNames(); rane.hasMoreElements();) {
            String key = rane.nextElement();
            Object value = session.getAttribute(key);
            if (value != null) {
                log.info(" - " + key + " = " + value);
            }
        }

        log.info("Session-attributes:");
        for (Enumeration<String> sane = session.getAttributeNames(); sane.hasMoreElements();) {
            String key = sane.nextElement();
            log.info(" - " + key + " = " + session.getAttribute(key));
        }

        Set<Map.Entry<String, Object>> me = model.asMap().entrySet();
        log.info("ModelElements (" + me.size() + "):");
        for (Map.Entry<String, Object> e: me) {
            log.info(" - " + e.getKey() + " = " + e.getValue());
        }
    }

    /**
     * Get the current UserProfile from the http session
     *
     * @param session
     * @param userId
     * @return
     */
    protected User getUserProfile(HttpSession session, String userId) {
        User profile = (User) session.getAttribute(USER_PROFILE);

        // Reload from persistence storage if not set or invalid (i.e. no valid userId)
        if (profile == null || !userId.equals(profile.getUserId())) {
            profile = usersDao.findOne(userId);
            session.setAttribute(USER_PROFILE, profile);
        }
        return profile;
    }

    /**
     * Get the current UserSocialConnection from the http session
     *
     * @param session
     * @param userId
     * @return
     */
    public MongoConnection getUserConnection(HttpSession session, String userId) {
        MongoConnection connection;
        connection = (MongoConnection) session.getAttribute(USER_CONNECTION);

        // Reload from persistence storage if not set or invalid (i.e. no valid userId)
        if (connection == null || !userId.equals(connection.getProviderId())) {
            connection = userSocialConnectionRepository.findByUserId(userId).get(0);
            session.setAttribute(USER_CONNECTION, connection);
        }
        return connection;
    }

    /**
     * Compile the best display name from the connection and the profile
     *
     * @param connection
     * @param profile
     * @return
     */
    protected String getDisplayName(MongoConnection connection, User profile) {

        // The name is set differently in different providers so we better look in both places...
        if (connection.getDisplayName() != null) {
            return connection.getDisplayName();
        } else {
            return profile.getFirstName();
        }
    }
}
