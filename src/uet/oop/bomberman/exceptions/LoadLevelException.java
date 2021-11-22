package uet.oop.bomberman.exceptions;

/**
 * Link tham khảo: https://shareprogramming.net/tu-dinh-nghia-mot-exception-trong-java/
 */
public class LoadLevelException extends GameException {
    public LoadLevelException() {
    }

    public LoadLevelException(String str) {
        super(str);

    }

    public LoadLevelException(String str, Throwable cause) {
        super(str, cause);

    }

    public LoadLevelException(Throwable cause) {
        super(cause);

    }

}
