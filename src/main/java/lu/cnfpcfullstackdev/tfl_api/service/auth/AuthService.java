package lu.cnfpcfullstackdev.tfl_api.service.auth;

import lu.cnfpcfullstackdev.tfl_api.dto.request.RegisterRequestDTO;
import lu.cnfpcfullstackdev.tfl_api.dto.request.LoginRequestDTO;
import lu.cnfpcfullstackdev.tfl_api.dto.response.AuthResponseDTO;
import lu.cnfpcfullstackdev.tfl_api.entity.User;
import lu.cnfpcfullstackdev.tfl_api.entity.UserRole;
import lu.cnfpcfullstackdev.tfl_api.exception.EmailAlreadyExistsException;
import lu.cnfpcfullstackdev.tfl_api.exception.InvalidCredentialsException;
import lu.cnfpcfullstackdev.tfl_api.exception.UsernameAlreadyExistsException;
import lu.cnfpcfullstackdev.tfl_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtService jwtService;
    
    public AuthResponseDTO register(RegisterRequestDTO dto) {
        // Check if username already exists
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UsernameAlreadyExistsException(dto.getUsername());
        }
        
        // Check if email already exists
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException(dto.getEmail());
        }
        
        // Create new user
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));  // Hash password!
        user.setRole(dto.getRole());
        
        // Set business fields if role is BUSINESS
        if (dto.getRole() == UserRole.BUSINESS) {
            user.setBusinessName(dto.getBusinessName());
            user.setBusinessAddress(dto.getBusinessAddress());
            user.setBusinessPhone(dto.getBusinessPhone());
        }
        
        // Save user
        User savedUser = userRepository.save(user);
        
        // Generate JWT token
        String token = jwtService.generateToken(savedUser);
        
        // Return response
        return new AuthResponseDTO(
            token,
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getRole().toString()
        );
    }
    
    public AuthResponseDTO login(LoginRequestDTO dto) {
        // Find user by username
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException());
        
        // Check password
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        
        // Generate JWT token
        String token = jwtService.generateToken(user);
        
        // Return response
        return new AuthResponseDTO(
            token,
            user.getId(),
            user.getUsername(),
            user.getRole().toString()
        );
    }
}