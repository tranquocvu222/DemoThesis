package demo.homestay.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.homestay.model.Token;
import demo.homestay.repository.TokenRepository;
import demo.homestay.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenRepository tokenRepository;

	public Token save(Token token) {
		return tokenRepository.saveAndFlush(token);
	}

	@Override
	public Token findByToken(String token) {
		return tokenRepository.findByToken(token);
	}
}