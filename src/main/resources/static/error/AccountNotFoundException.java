import com.dwiki.atmsimulation.model.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AccountNotFoundException extends RuntimeException {

    private final Account accountg;

}