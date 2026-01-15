package lu.cnfpcfullstackdev.tfl_api.controller;

import jakarta.validation.Valid;
import lu.cnfpcfullstackdev.tfl_api.dto.request.LoginRequestDTO;
import lu.cnfpcfullstackdev.tfl_api.dto.request.RegisterRequestDTO;
import lu.cnfpcfullstackdev.tfl_api.dto.response.AuthResponseDTO;
import lu.cnfpcfullstackdev.tfl_api.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO dto) {
        AuthResponseDTO response = authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        AuthResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }
}