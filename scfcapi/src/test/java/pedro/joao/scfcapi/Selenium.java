package pedro.joao.scfcapi;

// Generated by Selenium IDE
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class Selenium {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before("")
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @After("")
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void naoDeveSalvarExamePraticoComCategoriaNulo() {
        driver.get("http://localhost:3000/");
        driver.manage().window().setSize(new Dimension(1050, 708));
        driver.findElement(By.linkText("Exames Práticos")).click();
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.id("inputDataExamePratico")).click();
        driver.findElement(By.id("inputDataExamePratico")).sendKeys("2025-02-21");
        driver.findElement(By.id("inputHorarioExamePratico")).click();
        driver.findElement(By.id("inputHorarioExamePratico")).sendKeys("18:43");
        driver.findElement(By.id("selectInstrutor")).click();
        {
            WebElement dropdown = driver.findElement(By.id("selectInstrutor"));
            dropdown.findElement(By.xpath("//option[. = 'Instrutor1 da Silva']")).click();
        }
        driver.findElement(By.id("selectVeiculo")).click();
        {
            WebElement dropdown = driver.findElement(By.id("selectVeiculo"));
            dropdown.findElement(By.xpath("//option[. = 'yamaha']")).click();
        }
        driver.findElement(By.id("inputLocal")).click();
        driver.findElement(By.id("inputLocal")).sendKeys("1");
        driver.findElement(By.cssSelector(".btn-success")).click();
        driver.findElement(By.cssSelector(".toast-message")).click();
        driver.close();
    }
    @Test
    public void naoDeveSalvarExamePraticoComDataNulo() {
        driver.get("http://localhost:3000/");
        driver.manage().window().setSize(new Dimension(1050, 708));
        driver.findElement(By.linkText("Exames Práticos")).click();
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.id("inputHorarioExamePratico")).click();
        driver.findElement(By.id("inputHorarioExamePratico")).sendKeys("18:51");
        driver.findElement(By.id("selectInstrutor")).click();
        {
            WebElement dropdown = driver.findElement(By.id("selectInstrutor"));
            dropdown.findElement(By.xpath("//option[. = 'Instrutor1 da Silva']")).click();
        }
        driver.findElement(By.id("selectVeiculo")).click();
        {
            WebElement dropdown = driver.findElement(By.id("selectVeiculo"));
            dropdown.findElement(By.xpath("//option[. = 'yamaha']")).click();
        }
        driver.findElement(By.id("inputCategoria")).click();
        {
            WebElement dropdown = driver.findElement(By.id("inputCategoria"));
            dropdown.findElement(By.xpath("//option[. = 'a']")).click();
        }
        driver.findElement(By.id("inputLocal")).click();
        driver.findElement(By.id("inputLocal")).sendKeys("1");
        driver.findElement(By.cssSelector(".btn-success")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".btn-success"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        driver.findElement(By.cssSelector(".toast-message")).click();
    }
    @Test
    public void naoDeveSalvarExamePraticoComHorarioNulo() {
        driver.get("http://localhost:3000/");
        driver.manage().window().setSize(new Dimension(1050, 708));
        driver.findElement(By.linkText("Exames Práticos")).click();
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.id("inputDataExamePratico")).click();
        driver.findElement(By.id("inputDataExamePratico")).sendKeys("2025-02-21");
        driver.findElement(By.id("selectInstrutor")).click();
        {
            WebElement dropdown = driver.findElement(By.id("selectInstrutor"));
            dropdown.findElement(By.xpath("//option[. = 'Instrutor1 da Silva']")).click();
        }
        driver.findElement(By.id("selectVeiculo")).click();
        {
            WebElement dropdown = driver.findElement(By.id("selectVeiculo"));
            dropdown.findElement(By.xpath("//option[. = 'yamaha']")).click();
        }
        driver.findElement(By.id("inputCategoria")).click();
        {
            WebElement dropdown = driver.findElement(By.id("inputCategoria"));
            dropdown.findElement(By.xpath("//option[. = 'a']")).click();
        }
        driver.findElement(By.id("inputLocal")).click();
        driver.findElement(By.id("inputLocal")).sendKeys("1");
        driver.findElement(By.cssSelector(".btn-success")).click();
        driver.findElement(By.cssSelector(".toast-message")).click();
    }
    @Test
    public void naoDeveSalvarExamePraticoComLocalNulo() {
        driver.get("http://localhost:3000/");
        driver.manage().window().setSize(new Dimension(1050, 680));
        driver.findElement(By.linkText("Exames Práticos")).click();
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.id("inputDataExamePratico")).click();
        driver.findElement(By.id("inputDataExamePratico")).click();
        driver.findElement(By.id("inputDataExamePratico")).sendKeys("2025-02-21");
        driver.findElement(By.id("inputHorarioExamePratico")).click();
        driver.findElement(By.id("inputHorarioExamePratico")).sendKeys("18:39");
        driver.findElement(By.id("selectInstrutor")).click();
        {
            WebElement dropdown = driver.findElement(By.id("selectInstrutor"));
            dropdown.findElement(By.xpath("//option[. = 'Instrutor1 da Silva']")).click();
        }
        driver.findElement(By.id("selectVeiculo")).click();
        {
            WebElement dropdown = driver.findElement(By.id("selectVeiculo"));
            dropdown.findElement(By.xpath("//option[. = 'yamaha']")).click();
        }
        driver.findElement(By.id("inputCategoria")).click();
        {
            WebElement dropdown = driver.findElement(By.id("inputCategoria"));
            dropdown.findElement(By.xpath("//option[. = 'a']")).click();
        }
        driver.findElement(By.cssSelector(".btn-success")).click();
        driver.findElement(By.cssSelector(".toast-message")).click();
    }
    @Test
    public void naoDeveSalvarExamePraticoComLocalVazio() {
        driver.get("http://localhost:3000/");
        driver.manage().window().setSize(new Dimension(1050, 708));
        driver.findElement(By.linkText("Exames Práticos")).click();
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.id("inputDataExamePratico")).click();
        driver.findElement(By.id("inputDataExamePratico")).sendKeys("2025-02-22");
        driver.findElement(By.id("inputHorarioExamePratico")).click();
        driver.findElement(By.id("inputHorarioExamePratico")).sendKeys("18:41");
        driver.findElement(By.id("selectInstrutor")).click();
        {
            WebElement dropdown = driver.findElement(By.id("selectInstrutor"));
            dropdown.findElement(By.xpath("//option[. = 'Instrutor1 da Silva']")).click();
        }
        driver.findElement(By.id("selectVeiculo")).click();
        {
            WebElement dropdown = driver.findElement(By.id("selectVeiculo"));
            dropdown.findElement(By.xpath("//option[. = 'yamaha']")).click();
        }
        driver.findElement(By.id("inputCategoria")).click();
        {
            WebElement dropdown = driver.findElement(By.id("inputCategoria"));
            dropdown.findElement(By.xpath("//option[. = 'a']")).click();
        }
        driver.findElement(By.id("inputLocal")).click();
        driver.findElement(By.id("inputLocal")).sendKeys("     ");
        driver.findElement(By.cssSelector(".btn-success")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".btn-success"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        driver.findElement(By.cssSelector(".toast-message")).click();
        driver.close();
    }
}
