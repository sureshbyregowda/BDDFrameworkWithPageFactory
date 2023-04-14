package com.ecommerce.ebay.stepdefs;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Reporter;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.ecommerce.ebay.hooks.*;
import com.ecommerce.ebay.pages.*;
import com.ecommerce.ebay.utilities.*;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Steps_ebay {
	private static CustomDriver driver;
	private static final Logger log = LogManager.getLogger(Steps_ebay.class.getName());
	private Page_ebay page_ebay;
	  
	public Steps_ebay(DriverHelper driverHelper)
	{
		page_ebay = new Page_ebay(driverHelper.driver);
	}	
	
	@Given("I navigate to the Website {string}")
	public void i_navigate_to_the_website(String url) {
		page_ebay.navigateToWebSite(url);
	}
	
	@When("I navigate to Search by {string}")
	public void i_navigate_to_search_by(String category) {
		page_ebay.Select_Item_from_Shop_by_Category(category);
	}

	@When("I click {string} in the left hand side navigation section")
	public void i_click_in_the_left_hand_side_navigation_section(String expectedbreadcrumbText) {
		page_ebay.Select_Item_in_Left_hand_Side_navigation(expectedbreadcrumbText);
	}

	@When("I click {string} button appears under Shop by brand or Shop by Network")
	public void i_click_button_appears_under_shop_by_brand_or_shop_by_network(String seeAll) {
		page_ebay.Select_all_Button(seeAll);
	}

	@When("I Add three filters apperaing in the pop up and click apply button")
	public void i_add_three_filters_apperaing_in_the_pop_up_and_click_apply_button(DataTable filterdetails) {
		List<List<String>> fildetails = filterdetails.asLists(String.class);
		//Read all filter value details from the Data Table
		String screenSize = fildetails.get(1).get(0);   //screenSize	
		String priceFrom = fildetails.get(1).get(1);  	//price from
		String priceTo = fildetails.get(1).get(2);  	//price from		
		String location = fildetails.get(1).get(3);   	//location
		page_ebay.Select_filters_In_Popup(screenSize,priceFrom,priceTo,location);
	}	
	

	@Then("Verify that the filter tags are applied")
	public void verify_that_the_filter_tags_are_applied() {
		page_ebay.Verify_filters_applied();
	}  
	
	@When("I type {string} in the search bar\"")
	public void i_type_in_the_search_bar(String searchvalue) {
		page_ebay.Enter_Search_Value(searchvalue);
	}

	@When("I Change the search category {string} and click search button")
	public void i_change_the_search_category_and_click_search_button(String category) {
		page_ebay.change_Search_category(category);
	}

	@Then("Verify that the page loads completely")
	public void verify_that_the_page_loads_completely() {
		page_ebay.Verify_Page_loads_after_search();
	}

	@Then("Verify that the first result name matches with the search string {string}")
	public void verify_that_the_first_result_name_matches_with_the_search_string(String searchtext) {
		page_ebay.Verify_search_results(searchtext);
	}	

}
