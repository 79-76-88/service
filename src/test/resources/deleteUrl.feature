Feature: delete url with password

  Scenario: client tries to delete url with password
    When the client calls delete short-link
    Then the client receives status code of 200

