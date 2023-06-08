Feature: Delete client functionality

 Background: User is logged to the HRM system
    Given User logged in with email "admin@demo.com" and password "riseDemo"
    
    @delete
    Scenario Outline: Confirm deletion of a record
    Given User navigates to client page
    And User searches with value "<CompanyName>"
    When User click the icon Delete next to the record with name "<CompanyName>"
    And User click Delete button in the confirmation modal to delete record
    Then User receives a alert message of successful record deletion
    
    Examples:
    | CompanyName  |
    | hanh         |
    
    @cancel
    Scenario Outline: Confirm cancel deletion of a record
    Given User navigates to client page
    And User searches with value "<CompanyName>"
    When User click the icon Delete next to the record with name "<CompanyName>"
    And User click Cancel button in the confirmation modal to delete record
    Then The record with name "<CompanyName>" is not removed from the list
    
    Examples:
    | CompanyName  |
    | hanhbui      |
    
    


    