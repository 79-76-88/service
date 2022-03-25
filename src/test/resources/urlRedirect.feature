Feature: redirect link

  Scenario: client makes call to GET short-link/redirect/fb
    When the client calls short-link/redirect/fb
    Then the client receives status code of 302

