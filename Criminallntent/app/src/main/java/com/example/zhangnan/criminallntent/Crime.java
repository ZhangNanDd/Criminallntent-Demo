package com.example.zhangnan.criminallntent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhangnan on 16/9/18.
 */
public class Crime {
    private UUID id;
    private String title;
    private Date date;
    private boolean solved;

    public Crime(){
        this(UUID.randomUUID());

    }

    public Crime(UUID id){
        this.id = id;
        date = new Date();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
//        Locale locale = new Locale("en","US");
//        /*
//        第一种方式
//         */
//        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日EEEE,HH:mm:ss",locale);
//        //String time=dateFormat.format(date);
//        /*
//        第二种方式
//         */
//        DateFormat fullDateFormat = DateFormat.getDateInstance(DateFormat.FULL,locale);
//        String time = fullDateFormat.format(date);
//        Date dateTrans = fullDateFormat.parse(time);//问题:是直接返回String好还是返回DATE
//        return dateTrans;

    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}
