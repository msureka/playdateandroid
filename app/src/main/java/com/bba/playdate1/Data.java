package com.bba.playdate1;

/**
 * Created by Android on 3/1/2017.
 */
public class Data {

    //Discver Profile Model class

    //Varibales
    private String Type;
    private String FbId;
    private String Title;
    private String AdDescription;
    private String SubTitle;
    private String ColorRed;
    private String ColorGreen;
    private String ColorBlue;
    private String ImageTitle;
    private String ImageLogo1;
    private String ImageLogo2;
    private String ImageLogo3;
    private String UserId;
    private String EmailId;
    private String Fname;
    private String Gender;
    private String BirthDate;
    private String EngLang;
    private String ArbicLang;
    private String FrenchLang;
    private String City;
    private String Country;
    private String Description;
    private String LikeToPlay;
    private String ICanMeet;
    private String Activity1;
    private String Activity2;
    private String Activity3;
    private String Emoji1;
    private String Emoji2;
    private String Emoji3;
    private String SuperHero;
    private String ProfilePic;
    private String Age;


    public Data(String type, String fbid, String title, String addesc, String subtitle, String colorred, String colorgreen, String colorblue, String imagetilte, String imagelogo1, String imagelogo2, String imagelogo3, String userid, String emailid, String fname, String gender, String birtdate, String englang, String arbiclang, String frenchlang, String city, String country, String desciption, String liketoplay, String icanmeet, String activity1, String activity2, String activity3, String emoji1, String emoji2, String emoji3, String superhero, String pic, String age) {

        //set Variable to current user Daetails
        this.Type = type;
        this.FbId = fbid;
        this.Title = title;
        this.AdDescription = addesc;
        this.SubTitle = subtitle;
        this.ColorRed = colorred;
        this.ColorGreen = colorgreen;
        this.ColorBlue = colorblue;
        this.ImageTitle = imagetilte;
        this.ImageLogo1 = imagelogo1;
        this.ImageLogo2 = imagelogo2;
        this.ImageLogo3 = imagelogo3;
        this.UserId = userid;
        this.EmailId = emailid;
        this.Fname = fname;
        this.Gender = gender;
        this.BirthDate = birtdate;
        this.EngLang = englang;
        this.ArbicLang = arbiclang;
        this.FrenchLang = frenchlang;
        this.City = city;
        this.Country = country;
        this.Description = desciption;
        this.LikeToPlay = liketoplay;
        this.ICanMeet = icanmeet;
        this.Activity1 = activity1;
        this.Activity2 = activity2;
        this.Activity3 = activity3;
        this.Emoji1 = emoji1;
        this.Emoji2 = emoji2;
        this.Emoji3 = emoji3;
        this.SuperHero = superhero;
        this.ProfilePic = pic;
        this.Age = age;
    }


    public String getType() {
        return Type;
    }

    public String getFbId() {
        return FbId;
    }

    public String getTitle() {
        return Title;
    }

    public String getAdDescription() {
        return AdDescription;
    }

    public String getSubTitle() {
        return SubTitle;
    }

    public String getColorRed() {
        return ColorRed;
    }

    public String getColorGreen() {
        return ColorGreen;
    }

    public String getColorBlue() {
        return ColorBlue;
    }

    public String getImageTitle() {
        return ImageTitle;
    }

    public String getImageLogo1() {
        return ImageLogo1;
    }

    public String getImageLogo2() {
        return ImageLogo2;
    }

    public String getImageLogo3() {
        return ImageLogo3;
    }

    public String getUserId() {
        return UserId;
    }

    public String getEmailId() {
        return EmailId;
    }

    public String getFname() {
        return Fname;
    }

    public String getGender() {
        return Gender;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public String getEngLang() {
        return EngLang;
    }

    public String getArbicLang() {
        return ArbicLang;
    }

    public String getFrenchLang() {
        return FrenchLang;
    }

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    public String getDescription() {
        return Description;
    }

    public String getLikeToPlay() {
        return LikeToPlay;
    }

    public String getICanMeet() {
        return ICanMeet;
    }

    public String getActivity1() {
        return Activity1;
    }

    public String getActivity2() {
        return Activity2;
    }

    public String getActivity3() {
        return Activity3;
    }

    public String getEmoji1() {
        return Emoji1;
    }

    public String getEmoji2() {
        return Emoji2;
    }

    public String getEmoji3() {
        return Emoji3;
    }

    public String getSuperHero() {
        return SuperHero;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public String getAge() {
        return Age;
    }
}

