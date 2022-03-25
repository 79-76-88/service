Feature: availability is returned on check

  Scenario: client makes call to POST facebook link as fb
    When the client calls short-link/set
    Then the client receives status code of 200
    And the client receives the url he posted

