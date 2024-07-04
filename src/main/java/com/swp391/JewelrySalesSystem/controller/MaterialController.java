package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.facade.MaterialFacade;
import com.swp391.JewelrySalesSystem.request.UpsertMaterialRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.MaterialResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/materials")
public class MaterialController {
  private final MaterialFacade materialFacade;

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get all materials",
      tags = {"Material APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("hasRole('ROLE_MANAGER')")
  BaseResponse<List<MaterialResponse>> getAllMaterial() {
    return this.materialFacade.getAllMaterial();
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Create material",
      tags = {"Material APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public BaseResponse<Void> createMaterial(@RequestBody UpsertMaterialRequest request) {
    return this.materialFacade.createMaterial(request);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Update material",
      tags = {"Material APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public BaseResponse<Void> updateMaterial(
      @PathVariable("id") Long id, @RequestBody UpsertMaterialRequest request) {
    return this.materialFacade.updateMaterial(id, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Delete material by Id",
      tags = {"Material APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public BaseResponse<Void> deleteMaterial(@PathVariable("id") Long id) {
    return this.materialFacade.deleteMaterial(id);
  }
}
