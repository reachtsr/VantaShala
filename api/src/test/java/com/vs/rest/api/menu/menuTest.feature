Feature: Create Empty Menu

  Background:
    * url baseUrl

  Scenario: Create and Retrieve a menu

    Given url '/menu'
    And request { name: 'Billie' }
    When method post
    Then status 201
    And match response == { id: '#notnull', name: 'Billie' }

    Given path response.id
    When method get
    Then status 200