//First attempt at Selenium Java test to check that details entered into Contact Us section of URL
// is displayed in the admin login portal and matches the entered data

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.*;
import java.time.Duration;

public class Browse {
    public static void main(String[] args) {
        //Set WebDriver location
        System.setProperty("webdriver.chrome.driver","C:\\chromedriver\\chromedriver.exe");
        //Open Chrome and load page
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("https://automationintesting.online/");
        //Wait and click 'Let me hack!' button
        new WebDriverWait(driver, Duration.ofSeconds(10) ).until(ExpectedConditions.elementToBeClickable((By.cssSelector("button[class='btn btn-primary'")))).click();
        //Populate data into the Contact Us section using faker
        Faker faker = new Faker();
        var name = faker.name().fullName();
        System.out.println("Entered name :- " + name);
        var emailAddress = faker.internet().emailAddress();
        System.out.println("Entered email address :- " + emailAddress);
        var phone = faker.phoneNumber().phoneNumber();
        System.out.println("Entered phone no. :- " + phone);
        var subject = faker.lorem().sentence(2);
        System.out.println("Entered subject :- " + subject);
        var message = faker.lorem().paragraph(4);
        System.out.println("Entered email message :- " + message);
        //Name
        driver.findElement(By.id("name")).sendKeys(name);
        //Email
        driver.findElement(By.id("email")).sendKeys(emailAddress);
        //Phone
        driver.findElement(By.id("phone")).sendKeys(phone);
        //Subject
        driver.findElement(By.id("subject")).sendKeys(subject);
        //Message
        driver.findElement(By.id("description")).sendKeys(message);
        //Submit Contact Us details
        driver.findElement(By.id("submitContact")).click();

        //Load admin portal
        Methods.createRoom(driver);
        //Methods.adminLogin(driver);
//        driver.navigate().to("https://automationintesting.online/#/admin");
//        //Login to admin page
//        driver.findElement(By.id("username")).sendKeys("admin");
//        driver.findElement(By.id("password")).sendKeys("password");
//        driver.findElement(By.id("doLogin")).click();
        //Click the inbox icon
        new WebDriverWait(driver, Duration.ofSeconds(2) ).until(ExpectedConditions.elementToBeClickable((By.cssSelector("i[class='fa fa-inbox'")))).click();
        //Check for the messages
        new WebDriverWait(driver, Duration.ofSeconds(2) ).until(ExpectedConditions.elementToBeClickable((By.cssSelector("[id^=message]"))));
        //Check if name is in the list and select
        Boolean isPresent = driver.findElements(By.xpath("//*[contains(text(),'"+name.trim()+"')]")).size() > 0;
        if (isPresent==true) {
            driver.findElement(By.xpath("//*[contains(text(),'" + name.trim() + "')]")).click();
            //wait for message to load
            new WebDriverWait(driver, Duration.ofSeconds(2) ).until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='btn btn-outline-primary']")));
            driver.findElement(By.cssSelector("[class='btn btn-outline-primary']"));
            //Get all the elements from the message and check contents
            var messageDetails = driver.findElement(By.cssSelector("[data-testid='message']")).getText();
            System.out.println("Displayed details:- " + messageDetails);
            //Name
            String from = driver.findElement(By.xpath("//div[contains(@class,'col-10')]")).getText();
            //remove label from text
            String actualName = from.substring(from.indexOf(": ") + 1);
            System.out.println(actualName);
            //Compare to entered text
            assertEquals(name.trim(), actualName.trim());
            //Phone
            String phoneNo = driver.findElement(By.xpath("//div[contains(@class,'col-2')]")).getText();
            //System.out.println(phoneNo);
            //remove label from text
            String actualPhoneNo = phoneNo.substring(phoneNo.indexOf(": ") + 1);
            System.out.println(actualPhoneNo);
            //Compare to entered text
            assertEquals(phone.trim(), actualPhoneNo.trim());
            //email
            String emailAdd = driver.findElement(By.xpath("//div[contains(@class,'col-12')]")).getText();
            //System.out.println(emailAdd);
            //remove label from text
            String actualEmailAdd = emailAdd.substring(emailAdd.indexOf(": ") + 1);
            System.out.println(actualEmailAdd);
            //Compare to entered text
            assertEquals(emailAddress.trim(), actualEmailAdd.trim());
            //subject
            if (messageDetails.contains(subject)) {
                System.out.println("Contains subject:- " + subject);
                //message
                if (messageDetails.contains(message)) {
                    System.out.println("Contains message:- " + message);
                    driver.findElement(By.cssSelector("[class='btn btn-outline-primary']")).click();
                } else {
                    System.out.println(message + " not found in the email");
                    boolean messageMatch = false;
                    assertTrue(messageMatch);
                }
            } else {
                System.out.println(subject + " not found in the email");
                boolean subjectMatch = false;
                assertTrue(subjectMatch);
            }

//            OLD WAY TO CHECKS MESSAGE USING NESTED IFs
//            List<WebElement> elements = driver.findElements(By.cssSelector("[data-testid='message']"));
//            System.out.println("Size of List: "+elements.size());
//            java.util.Iterator<WebElement> i = elements.iterator();
//            while (i.hasNext()) {
//                WebElement element = i.next();
//                if (element.isDisplayed()) {
//                    var messageDetails = driver.findElement(By.cssSelector("[data-testid='message']")).getText();//element.getText();
//                    System.out.println(i);
//                    System.out.println(messageDetails);
//                    if (messageDetails.contains("From")) {
//                        Assert.isTrue(messageDetails.contains(name), messageDetails + " doesn't contain:- " + name + ".");
//                        System.out.println("Contains Name:- " + name);
//                        if (messageDetails.contains("Email")) {
//                            Assert.isTrue(messageDetails.contains(emailAddress), messageDetails + " doesn't contain:- " + emailAddress + ".");
//                            System.out.println("Contains email:- " + emailAddress);
//                            if (messageDetails.contains("Phone")) {
//                                Assert.isTrue(messageDetails.contains(phone), messageDetails + " doesn't contain:- " + phone + ".");
//                                System.out.println("Contains phone no.:- " + phone);
//                                if (messageDetails.contains(subject)) {
//                                    System.out.println("Contains subject:- " + subject);
//                                    if (messageDetails.contains(message)) {
//                                        System.out.println("Contains message:- " + message);
//                                        driver.findElement(By.cssSelector("[class='btn btn-outline-primary']")).click();
//                                    } else {
//                                        System.out.println(message + " not found in the email");
//                                    }
//                                } else {
//                                    System.out.println(subject + " not found in the email");
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        } else {
            System.out.println(name + " not found in the list");
        }
        driver.close();
    }
}

