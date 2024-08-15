package pedro.joao.scfcapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("pedro.joao.scfcapi.api.controller"))
                .paths(PathSelectors.any())
                .build()
                //.securityContexts(Arrays.asList(securityContext()))
                //.securitySchemes(Arrays.asList(apiKey()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("SCFC API")
                .description("API do Sistema de Controle de Formação de condutores")
                .version("1.0")
                .contact(contact())
                .build();
    }

   /* private String contact() {
        ArrayList<Contact> contatos = new ArrayList<Contact>();
        Contact pedro = new Contact("Pedro Esteves"
                , "https://github.com/Pedro-Esteves-Al",
                "pedroestevesa@gmail.com");
        contatos.add(pedro);
        return contatos.toString();
    }*/

    private Contact contact(){
        return new Contact("Pedro Esteves/ João Vitor Pacheco"
                , "https://github.com/Pedro-Esteves-Al/LP3",
                "pedroestevesa@gmail.com");
    }


 /*   public ApiKey apiKey(){
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = authorizationScope;
        SecurityReference reference = new SecurityReference("JWT", scopes);
        List<SecurityReference> auths = new ArrayList<>();
        auths.add(reference);
        return auths;
    }*/

}
