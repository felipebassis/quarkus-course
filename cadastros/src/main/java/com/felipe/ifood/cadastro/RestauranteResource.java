package com.felipe.ifood.cadastro;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Restaurante")
@RolesAllowed("proprietario")
@SecurityScheme(securitySchemeName = "ifood-oauth",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token")
        )
)
@SecurityRequirement(name = "ifood-oauth")
public class RestauranteResource {

    @GET
    @Counted(name = "Quantidade buscas Restaurante")
    @SimplyTimed(name = "Tempo simples de busca")
    @Timed(name = "Tempo completo de busca")
    public List<Restaurante> getAll() {
        return Restaurante.listAll();
    }

    @POST
    @Transactional
    public Response addOne(Restaurante dto) {
        dto.persist();

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void updateOne(@PathParam("id") UUID id,
                          Restaurante dto) {
        Restaurante restaurante = Restaurante.<Restaurante>findByIdOptional(id)
                .orElseThrow(NotFoundException::new);

        restaurante.nome = dto.nome;

        restaurante.persist();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deleteOne(@PathParam("id") UUID id) {
        Restaurante.<Restaurante>findByIdOptional(id)
                .ifPresentOrElse(Restaurante::delete,
                        () -> {
                            throw new NotFoundException();
                        }
                );
    }


    @GET
    @Path("{id}/pratos")
    @Tag(name = "Prato")
    public List<Prato> getAllByRestaurante(@PathParam("id") UUID restauranteId) {
        Restaurante restaurante = Restaurante.<Restaurante>findByIdOptional(restauranteId)
                .orElseThrow(NotFoundException::new);
        return Prato.list("restaurante", restaurante);
    }

    @POST
    @Path("{id}/pratos")
    @Tag(name = "Prato")
    @Transactional
    public Response addOne(@PathParam("id") UUID restauranteID, Prato prato) {
        prato.restaurante = Restaurante.<Restaurante>findByIdOptional(restauranteID)
                .orElseThrow(NotFoundException::new);
        prato.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{restauranteId}/pratos/{id}")
    @Tag(name = "Prato")
    @Transactional
    public void updateOne(@PathParam("restauranteId") UUID restauranteId,
                          @PathParam("id") UUID pratoId,
                          Prato dto) {
        Prato prato = Prato.<Prato>findByIdOptional(pratoId)
                .orElseThrow(NotFoundException::new);

        prato.nome = dto.nome;
        prato.descricao = dto.descricao;

        prato.persist();
    }

    @DELETE
    @Path("{restauranteId}/pratos/{id}")
    @Tag(name = "Prato")
    @Transactional
    public void deleteOne(@PathParam("restauranteId") UUID restauranteId,
                          @PathParam("id") UUID pratoId) {
        Prato.<Prato>findByIdOptional(pratoId)
                .orElseThrow(NotFoundException::new)
                .delete();
    }
}
