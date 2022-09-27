import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.Assert.*;

public class Methods {
    //add faker for methods
    static Faker faker = new Faker();

    //Admin Login
    public static void adminLogin(WebDriver driver) {
        //Load the admin login page
        driver.navigate().to("https://automationintesting.online/#/admin");
        //Login to admin page
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("doLogin")).click();
    }

    //Select random dropdown value
    public static void randomListValue(WebElement value) {
        List<WebElement> itemsInDropdown = value.findElements(By.cssSelector("option[value]"));
        //Click random type
        int size = itemsInDropdown.size();
        System.out.println("Number of Items in dropdown is " + size);
        int randomNumber = ThreadLocalRandom.current().nextInt(0,size);
        System.out.println("Random number from Number of items is " + randomNumber);
        System.out.println("Corresponding text in dropdown for random value is " + itemsInDropdown.get(randomNumber).getText());
        itemsInDropdown.get(randomNumber).click();
    }

    //create a new room to book
    public static void createRoom(WebDriver driver) {
        //login to admin section
        adminLogin(driver);
        //select the create room link
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable((By.cssSelector("a[class='nav-link']")))).click();
        //driver.findElement(By.cssSelector("a[class='nav-link']")).click();
        //add room number
        String roomNumber = faker.number().digits(3);
        System.out.println("Room number is set as " + roomNumber);
        driver.findElement(By.id("roomName")).sendKeys(roomNumber);
        //room type
        WebElement drpRoom = driver.findElement(By.id("type"));
        randomListValue(drpRoom);
        //select accessible
        WebElement drpAccessible = driver.findElement(By.id("accessible"));
        randomListValue(drpAccessible);
        //add room price, did not like using a decimal value even when set as a string
        //double price = faker.number().randomDouble(2, 50, 300);
        String roomPrice = faker.number().digits(3);
        System.out.println("Room price is set as " + roomPrice);
        driver.findElement(By.id("roomPrice")).sendKeys(roomPrice);
        //select room details from radio list
        List<WebElement> a = driver.findElements(By.cssSelector("[type='checkbox']"));
        Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(a.size());
        if (int_random == 0) {
            int_random = int_random+1;
        }
        System.out.println("Random value for detail selection loop is " + int_random);
        for(int i = 0; i < a.size(); i += int_random) {
            a.get(i).click();
            System.out.println("Selected option " + a.get(i).getAttribute("value"));
        }
        //click the create button
        driver.findElement(By.id("createRoom")).click();
    }

    //Book a room
    public static void bookRoom(WebDriver driver) {
        //Click the Book room button
        driver.findElement(By.cssSelector("[class='btn btn-outline-primary float-right openBooking']")).click();
        //Select today's date plus tomorrow with a left mouse click drag
        WebElement startDate = driver.findElement(By.cssSelector("[class='rbc-date-cell rbc-now rbc-current']"));
        //Drag and release to tomorrow
        Actions action = new Actions(driver);
        action.clickAndHold(startDate).pause(Duration.ofSeconds(2))
                .moveByOffset(0,10)
                .moveByOffset(50, 0)
                .release().build().perform();
        //Create and enter Customer details
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = firstName+lastName+"@gmail.com";
        String phone = faker.phoneNumber().phoneNumber();

        driver.findElement(By.cssSelector("[name='firstname']")).sendKeys(firstName);
        driver.findElement(By.cssSelector("[name='lastname']")).sendKeys(lastName);
        driver.findElement(By.cssSelector("[name='email']")).sendKeys(email);
        driver.findElement(By.cssSelector("[name='phone']")).sendKeys(phone);
        //Click the Book button
        driver.findElement(By.cssSelector("[class='btn btn-outline-primary float-right book-room']")).click();
        //Confirm success pop up
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable((By.cssSelector("[class='ReactModal__Content ReactModal__Content--after-open confirmation-modal']"))));
        assertTrue(driver.findElement(By.cssSelector("[class='ReactModal__Content ReactModal__Content--after-open confirmation-modal']")).isDisplayed());
        //could check pop up text
        //Close success pop up
        driver.findElement(By.cssSelector("[class='btn btn-outline-primary']")).click();
    }
}