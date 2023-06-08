Feature: Import clients functionality
 Background: User is logged to the HRM system
    Given User logged in with email "admin@demo.com" and password "riseDemo"

  @failure
  Scenario Outline: User uploaded the wrong file format
    Given User navigates to Import clients page
    When User upload files from given sheetname "<SheetName>" and rownumber <RowNumber>
    Then User receives an error message right under the files field
    
    Examples:
    | SheetName     | RowNumber |
    | ImportClients | 0         |
    | ImportClients | 1         |
    | ImportClients | 2         |
    
    
  @failure
  Scenario: User uploaded the correct file format but wrong template
    Given User navigates to Import clients page
    When User upload files with wrong template
    | file_upload                           |
    | C:\Users\Admin\Downloads\Login.xlsx   |
    And User clicks on Next button
    Then The user redirect to check detail page and the error message is displays
    
    
  
    
    
    
    