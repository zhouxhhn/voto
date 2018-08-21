package bjl.domain.model.sequence;

/**
 * Created by pengyi on 2016/3/22.
 */
public class Sequence {

    private String id;
    private long no;

    public String getId() {
        return id;
    }

    public long getNo() {
        return no;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public Sequence() {
    }

    public Sequence(long no) {
        this.no = no;
    }
}
