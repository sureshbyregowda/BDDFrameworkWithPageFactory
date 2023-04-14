package com.ecommerce.ebay.pages;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ecommerce.ebay.utilities.CustomDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.support.ui.Select;


public class Page_ebay {
	
	private CustomDriver driver;
	private static final Logger log = LogManager.getLogger(Page_ebay.class.getName());	

	public Page_ebay(WebDriver driver) {
		this.driver = new CustomDriver(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="gh-la") private WebElement eBayLogo;
	@FindBy(id="gh-shop-a") private WebElement shopBycaterogyButton;
	@FindBy(id="gh-cat-box") private WebElement selectBycaterogyButton;
	@FindBy(xpath="//*[@id='gh-sbc']//li/a[contains(text(),'Cell phones & accessories')]") private WebElement select_CellPhones_accessories_Button;	
	@FindBy(xpath="//ul/li[3]/a[@class='seo-breadcrumb-text']") private WebElement breadcrumbText;
	@FindBy(xpath="//ul/li[4]/a[@class='seo-breadcrumb-text']") private WebElement breadcrumb_text_CellPhone_SmartPhones;
	
	@FindBy(xpath="//*[@id='gh-cat-box']/select[@id='gh-cat']") private WebElement select_category_dropdown;
	@FindBy(xpath="//span[contains(text(),'Shop by Brand')]/preceding-sibling::span") private WebElement see_all_button;
	@FindBy(xpath="//div[@class='dialog__window']") private WebElement dialog_window;
	
	@FindBy(xpath="//li/a[contains(text(),'Cell Phones & Smartphones')]") private WebElement CellPhones_Smartphones_Link;	
	@FindBy(xpath="//*[@role='tab' and @data-aspecttitle='aspect-Screen%20Size']") private WebElement screen_size_tab;	
	@FindBy(xpath="//*[@role='tab' and @data-aspecttitle='location']") private WebElement location_tab;	
	@FindBy(xpath="//*[@role='tab' and @data-aspecttitle='price']") private WebElement price_tab;
	
	@FindBy(id="c3-subPanel-Screen%20Size_4.0%20-%204.4%20in") private WebElement screen_size_chk_box;	
	@FindBy(xpath="//input[@aria-label='Minimum Value, US Dollar']") private WebElement input_price_from;		
	@FindBy(xpath="//input[@aria-label='Maximum Value, US Dollar']") private WebElement input_price_to;	
	@FindBy(xpath="//input[@value='Worldwide']") private WebElement worldwide_chk_box;		
	@FindBy(xpath="//button[@aria-label='Apply']") private WebElement apply_button;		
	
	@FindBy(xpath="//button[@aria-label='Apply']") private WebElement filter_applied_button;		
	@FindBy(xpath="//li[@class='brm__aspect-item brm__aspect-item--applied']/a/span[contains(text(), 'Screen Size')]") private WebElement size_filter_applied;	
	@FindBy(xpath="//li[@class='brm__aspect-item brm__aspect-item--applied']/a/span[contains(text(), 'Price')]']") private WebElement price_filter_applied;	
	@FindBy(xpath="//li[@class='brm__aspect-item brm__aspect-item--applied']/a/span[contains(text(), 'Item Location')]") private WebElement location_filter_applied;	
	
	
	@FindBy(id="gh-ac") private WebElement search_textbox;
	@FindBy(id="gh-btn") private WebElement search_button;	
	
	@FindBy(xpath="//span[@role='heading']/span[@class='BOLD' and contains(text(),'Apple Macbook')]") private WebElement search_first_result;	
	@FindBy(xpath="//span[@aria-label='All Listings Current view']") private WebElement all_listings_link;	
		
	
	//Navigate to Website
	public void navigateToWebSite(String url)
	{
		//Launch URL
		driver.getDriver().get(url);
		String originalTitle = driver.getDriver().getTitle().strip();
		String expectedTitle = "Electronics, Cars, Fashion, Collectibles & More | eBay";			
		Assert.assertEquals(originalTitle,expectedTitle,"Titles of the website do not match");
	}
    
	//Select Item from Shop by Category
	public void Select_Item_from_Shop_by_Category(String expectedcategory)
	{
		shopBycaterogyButton.click();
		select_CellPhones_accessories_Button.click();		
		String [] expctedText = expectedcategory.split(">");		
		Assert.assertEquals(breadcrumbText.getText().strip(), expctedText[2].strip(), "Category not selected");		
	}

	//Select Category in left hand side navigation
	public void Select_Item_in_Left_hand_Side_navigation(String expectedbreadcrumbText)
	{
		CellPhones_Smartphones_Link.click();	
		Assert.assertEquals(breadcrumb_text_CellPhone_SmartPhones.getText().strip(), expectedbreadcrumbText.strip(), "Category not selected");			
	}	
	
	//Select All button appears under Shop by brand or Shop by Network
	public void Select_all_Button(String Item)
	{
		see_all_button.click();
		//Check for dialog window
		if(dialog_window.isDisplayed())
		{
			log.info("dialog window opened successfully");
		}
		else
		{
			log.info("dialog window failed to load");
		}
	}	

	//Select filters	
	public void Select_filters_In_Popup(String screenSize,String priceFrom,String priceTo,String location)
	{		
		//Select Screen Size
		screen_size_tab.click();
		
		//select screen size of value 4.0 - 4.4 in
		screen_size_chk_box.click();
		
		//Select Items price from 1 to 10
		price_tab.click();
		input_price_from.sendKeys(priceFrom.strip());
		input_price_to.sendKeys(priceTo.strip());
		
		//Select location with value worldwide
		location_tab.click();
		worldwide_chk_box.click();
		
		//Click Apply button
		filter_applied_button.click();
	}	
	
	public void Verify_filters_applied()
	{
		filter_applied_button.click();
		if(size_filter_applied.isSelected() && price_filter_applied.isSelected() && location_filter_applied.isSelected())
		{
			log.info("filters are selected successfully");
		}
		else
		{
			log.error("Error in while selecting filters");
		}
	}	
	
	//Enter value in Search box
	public void Enter_Search_Value(String searchvalue)
	{
		search_textbox.clear();
		search_textbox.sendKeys(searchvalue);
		
		String actualtext = search_textbox.getAttribute("value");
		Assert.assertEquals(actualtext.strip(), searchvalue.strip(), "Text not entered in search field");	
	}	
	
	//Change Search Category
	public void change_Search_category(String category)
	{
		selectBycaterogyButton.click();		
		//select sel = new select(select_category_dropdown);		
		Select sel = new Select(select_category_dropdown);		
		sel.selectByVisibleText(category);		
		//Click on search button
		search_button.click();				
	}	
	
	public void Verify_Page_loads_after_search()
	{	
		//After page loads check for All listings link
		if(all_listings_link.isDisplayed())
		{
			log.info("Page loaded successfully with search results");
		}
		else
		{
			log.error("Page not loaded with search results");
		}
	}		
	
	//Verify search results
	public void Verify_search_results(String searchtext)
	{	
		if(search_first_result.isDisplayed())
		{
			log.info("Search results found for the search input  "+searchtext);
		}
		else
		{
			log.error("No search resutls for "+searchtext);
		}
	}	
}




