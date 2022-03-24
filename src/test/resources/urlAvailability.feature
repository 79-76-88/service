Feature: availability is returned on check

  Scenario: client makes call to GET short-link/check/fb
    When the client calls short-link/check/fb
    Then the client receives status code of 200
    And the client receives response that url is not available

