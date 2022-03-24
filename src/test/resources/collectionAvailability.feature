Feature: availability is returned on check

  Scenario: client makes call to GET collection/check/fb
    When the client calls collection/check/fb
    Then the client receives status code of 200
    And the client receives response that url is available