package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.factory.Factory;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductResource.class)
class ProductResourceTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    // Vamos precisar do ObjectMapper para converter o obj ProductDTO para JSON
    @Autowired
    private ObjectMapper objectMapper;

    private long existingId;
    private long dependentId;
    private long nonExistingId;

    private ProductDTO productDTO;
    private PageImpl<ProductDTO> page;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        dependentId = 2L;
        nonExistingId = 3L;

        productDTO = Factory.createProductDTO();

        // Instanciando uma página de productDTO
        page = new PageImpl<>(List.of(productDTO));

        when(service.findAllPaged(any())).thenReturn(page);
        when(service.save(any())).thenReturn(productDTO);
        when(service.findById(existingId)).thenReturn(productDTO);
        when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        doNothing().when(service).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
        doThrow(DatabaseException.class).when(service).delete(dependentId);

        // Vamos precisar do eq() para evitar alguns erros de compilação
        when(service.update(eq(existingId), any())).thenReturn(productDTO);

        // Aqui é ResourceNotFoundException, pois o método update do service pega a exceção EntityNotFoundException
        // e lança a ResourceNotFoundException baseada nela
        when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void findAllShouldReturnPage() throws Exception {
        // O método perform faz uma requisição HTTP para a URI especificada
        ResultActions result = mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    void findByIdShouldReturnProductDTOWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/products/{id}", existingId).accept(MediaType.APPLICATION_JSON));


        // O método jsonPath() analisa o corpo da resposta, o $ acessa o obj da resposta, e o .id acessa o atributo id
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/products/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    void updateShouldReturnProductDTOWhenIdExists() throws Exception {
        // Vamos precisar converter o nosso obj ProductDTO (java) para JSON (texto), que é o formato do corpo da requisição
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result = mockMvc.perform(put("/products/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result = mockMvc.perform(put("/products/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    void createShouldReturnProductDTOCreated() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result = mockMvc.perform(post("/products")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/products/{id}", existingId));

        result.andExpect(status().isNoContent());
    }

    @Test
    void deleteShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/products/{id}", nonExistingId));

        result.andExpect(status().isNotFound());
    }
}
