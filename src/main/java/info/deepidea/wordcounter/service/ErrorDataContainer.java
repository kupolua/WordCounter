package info.deepidea.wordcounter.service;

public class ErrorDataContainer {
    private String respMessage;

    public ErrorDataContainer() {
    }

    public ErrorDataContainer(String message) {
        this.respMessage = message;
    }

    public String getRespMessage() {
        return respMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorDataContainer that = (ErrorDataContainer) o;

        if (respMessage != null ? !respMessage.equals(that.respMessage) : that.respMessage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return respMessage != null ? respMessage.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ErrorDataContainer{" +
                "respMessage='" + respMessage + '\'' +
                '}';
    }
}
