package com.kikopolis.task;

import com.kikopolis.util.DayOfWeek;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public abstract sealed class AudioTask implements Serializable permits RecurringAudioTask, SingularAudioTask {
    public static final String EMPTY_NAME = "empty_name";
    public static final String EMPTY_SOUND = "empty_sound";
    public static final Integer EMPTY_HOUR = -12;
    public static final Integer EMPTY_MINUTE = -12;
    private final UUID id;
    private String name;
    private String sound;
    private Integer hour;
    private Integer minute;
    private boolean isDispatched;
    
    protected AudioTask(final String name, final String sound, final Integer hour, final Integer minute, final boolean isDispatched) {
        id = UUID.randomUUID();
        this.name = name != null ? name : EMPTY_NAME;
        this.sound = sound != null ? sound : EMPTY_SOUND;
        this.hour = hour != null ? hour : EMPTY_HOUR;
        this.minute = minute != null ? minute : EMPTY_MINUTE;
        this.isDispatched = isDispatched;
    }
    
    public static AudioTask createFromCsv(final String[] csv) {
        return switch (csv[0]) {
            case TaskType.RECURRING_AUDIO_TASK -> new RecurringAudioTask(
                    csv[1],
                    csv[2],
                    Integer.parseInt(csv[3]),
                    Integer.parseInt(csv[4]),
                    DayOfWeek.valueOf(csv[5]),
                    DayOfWeek.valueOf(csv[6])
            );
            case TaskType.SINGULAR_AUDIO_TASK -> new SingularAudioTask(
                    csv[1],
                    csv[2],
                    Integer.parseInt(csv[3]),
                    Integer.parseInt(csv[4]),
                    Boolean.parseBoolean(csv[5]),
                    LocalDate.parse(csv[6])
            );
            default -> throw new IllegalArgumentException("Unknown Task type: " + csv[0]);
        };
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSound() {
        return sound;
    }
    
    public void setSound(String sound) {
        this.sound = sound;
    }
    
    public Integer getHour() {
        return hour;
    }
    
    public void setHour(Integer hour) {
        this.hour = hour;
    }
    
    public Integer getMinute() {
        return minute;
    }
    
    public void setMinute(Integer minute) {
        this.minute = minute;
    }
    
    public boolean isDispatched() {
        return isDispatched;
    }
    
    public void setDispatched(boolean dispatched) {
        isDispatched = dispatched;
    }
    
    public abstract boolean isReadyForDispatch();
    
    public abstract Iterable<String> toCsv();
    
    public boolean isInvalid() {
        return name.equals(EMPTY_NAME)
                || sound.equals(EMPTY_SOUND)
                || Objects.equals(hour, EMPTY_HOUR)
                || Objects.equals(minute, EMPTY_MINUTE);
    }
}
