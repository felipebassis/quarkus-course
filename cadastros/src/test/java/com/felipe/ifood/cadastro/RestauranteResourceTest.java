package com.felipe.ifood.cadastro;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.junit.QuarkusTest;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
public class RestauranteResourceTest extends BaseIntegrationTest {

    @Test
    @DataSet("restaurante-cenario-1.yml")
    public void shouldReturnAllRestaurantsFromDataset() {
        String result = given()
                .when()
                .get("/restaurantes")
                .then()
                .statusCode(200)
                .extract()
                .asString();
        Approvals.verifyJson(result);
    }

}