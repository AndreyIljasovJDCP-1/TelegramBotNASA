import java.io.IOException;

@FunctionalInterface
public interface Sender {
    void send() throws IOException;
}
