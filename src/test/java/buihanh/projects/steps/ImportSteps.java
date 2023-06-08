package buihanh.projects.steps;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import buihanh.driver.DriverFactory;
import buihanh.helpers.ExcelHelpers;
import buihanh.helpers.Helpers;
import buihanh.keywords.WebUI;
import buihanh.projects.pages.ImportclientsPage;
import buihanh.projects.pages.SigninPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ImportSteps {

	WebDriver driver;
	private SigninPage signinPage;
	private ImportclientsPage importPage;
	private String fileupload, expResult;
	
	@Given("User logged in with email {string} and password {string}")
	private void user_logged_in_page_to_upload(String vaildEmailText, String vaildPasswordText) {
		driver = DriverFactory.getDriver();
		signinPage = new SigninPage(driver);
		signinPage.enterEmail(vaildEmailText);
		signinPage.enterPassword(vaildPasswordText);
		signinPage.clickOnSigninButton();
	}

	@Given("User navigates to Import clients page")
	public void user_navigates_to_import_clients_page() {
		importPage = new ImportclientsPage(driver);
		importPage.openImportPage();
	}

	@When("User upload files from given sheetname {string} and rownumber {int}")
	public void user_upload_files_from_given_sheetname_and_rownumber(String sheetname, Integer rownumber)throws InvalidFormatException, IOException {
		ExcelHelpers reader = new ExcelHelpers();
		String excelpath = Helpers.getCurrentDir()+"src\\test\\resources\\data\\data.xlsx";
		List<Map<String, String>> testdata = reader.getData(excelpath, sheetname);
		fileupload = testdata.get(rownumber).get("file_upload");
		expResult = testdata.get(rownumber).get("exception_result");
		importPage.handleUploadFile(fileupload);
	}


	@Then("User receives an error message right under the files field")
	public void user_receives_an_error_message_right_under_the_files_field() {
		WebUI.verifyEquals(importPage.getWarningMessageFile(), expResult);

	}
	@When("User upload files with wrong size")
	public void user_upload_files_with_wrong_size(DataTable dataTable) {
		List<Map<String,String>>datalist=dataTable.asMaps(String.class,String.class);
        for (Map<String,String> dataMap:datalist)
        {
    		String file = dataMap.get("file_upload");	
    		importPage.handleUploadFile(file);
        }	
	}

	@When("User clicks on Next button")
	public void user_clicks_on_next_button() {
	    importPage.clickOnNextButton();
	}

	@Then("The user redirect to check detail page and the error message is displays")
	public void the_user_redirect_to_check_detail_page_and_the_error_message_is_displays() {
		WebUI.verifyEquals(importPage.importModalTitle(), "Import clients");
		WebUI.verifyEquals(importPage.getWarningMessageTemplate(), "There has an invalid header. The indicated field should be Company name.");
	}

	@When("User upload files with wrong template")
	public void user_upload_files_with_wrong_content_format(DataTable dataTable) {
		List<Map<String,String>>datalist=dataTable.asMaps(String.class,String.class);
        for (Map<String,String> dataMap:datalist)
        {
    		String file = dataMap.get("file_upload");	
    		importPage.handleUploadFile(file);
        }
	}

}
