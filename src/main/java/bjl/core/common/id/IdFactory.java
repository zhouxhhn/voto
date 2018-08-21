package bjl.core.common.id;


import bjl.application.sequence.ISequenceAppService;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;

import java.util.Date;

/**
 * Created by pengyi on 2016/3/22.
 */
public class IdFactory {

    private ISequenceAppService sequenceAppService;

    private String key;

    private String prefix;

    private int suffixLength;

    private Date nextDay;

    public IdFactory() {
        setNextDay();
    }

    private void setNextDay() {
        Date now = new Date();
        this.nextDay = CoreDateUtils.addDay(now, 1);
    }

    public synchronized String getNextId() {
        if (CoreDateUtils.isSameDay(new Date(), nextDay)) {
            setNextDay();
            sequenceAppService.reset();
        }

        StringBuilder sb = new StringBuilder(prefix);
        sb.append(CoreDateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS"))
                .append(CoreStringUtils.fillZero(sequenceAppService.getNextSequence(suffixLength), suffixLength));

        return sb.toString();
    }

    public void initSequence() {
        sequenceAppService.initSequence();
    }

    public void setSequenceAppService(ISequenceAppService sequenceAppService) {
        this.sequenceAppService = sequenceAppService;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffixLength(int suffixLength) {
        this.suffixLength = suffixLength;
    }

}
