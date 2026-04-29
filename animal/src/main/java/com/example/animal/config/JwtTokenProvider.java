import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // 토큰 암호화에 사용할 비밀키 (최소 32자 이상의 랜덤 문자열 권장)
    private final String SECRET_KEY = "your-very-secret-key-should-be-very-long-and-secure";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    
    // 토큰 유효시간 (예: 1시간)
    private final long EXPIRATION_TIME = 3600000L;

    // 토큰 생성 메서드
    public String createToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userId) // 토큰 주인 (아이디)
                .setIssuedAt(now)    // 발행 시간
                .setExpiration(expiryDate) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 암호화 알고리즘
                .compact();
    }
    
    // (나중에 필요함) 토큰에서 아이디 추출하는 메서드
    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}