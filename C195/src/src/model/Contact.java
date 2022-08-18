package model;


public class Contact
{
    public int contactId;
    public String contactName;
    public String contactEmail;


    public Contact (int contactId, String contactName, String contactEmail)
    {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }


    public int getContactId() {
        return contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }


    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


    public void setEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }


    @Override
    public String toString()
    {
        return (contactName);
    }
}