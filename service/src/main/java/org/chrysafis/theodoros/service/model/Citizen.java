package org.chrysafis.theodoros.service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Citizen {

	@Id
    @NotEmpty(message = "Please Insert Tautotita!")
    @Size(min=8, max=8, message="Tautotita must have 8 chars!")    
    @Column(name="tautotita", length=8, nullable=false, unique=true)
    private String tautotita;
    
    /*
     * Οριοθετούμε το όνομα από 2 έως 50 χαρακτήρες. Δεν υπάρχει λόγος να καταναλώσουμε παραπάνω πόρους.
     */
    @NotEmpty(message = "Please insert name!")
    @Column(name="citizen_name", length=50, nullable=false, unique=false)
    @Size(min = 2, max = 50, message = "Name must be between 2-50 chars")
    private String citizenName;
    
    /*
     * Οριοθετούμε το επώνυμο από 2 έως 50 χαρακτήρες. Δεν υπάρχει λόγος να καταναλώσουμε παραπάνω πόρους.
     */
    @NotEmpty(message = "Please insert surname!")
    @Column(name="citizen_surname", length=50, nullable=false, unique=false)
    @Size(min = 2, max = 50, message = "Surname must be 2-50 chars")
    private String citizenSurname;
    
    @NotEmpty(message = "Please insert gender!")
    @Size(min=1, max=20, message="Gender must be 1-20 chars")
    @Column(name="citizen_gender", length=20, nullable=false, unique=false)
    private String citizenGender;
    
    
    @Pattern(message = "\"DOB must be like \"HH-MM-EEEE\"!", regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(19|20d{2})$")
    @Size(min = 10, max = 10, message = "DOB must be like \"HH-MM-EEEE\"!")
    @Column(name="citizen_dob", length=10, nullable=false, unique=false)
    private String citizenDoB;
    
    @Digits(integer = 9, fraction = 0, message = "Please insert 9 digits for AFM!")
    @Positive(message = "AFM must be positive number!")    
    @Column(name="citizen_afm", length=9, nullable=false, unique=false)
    private int citizenAfm;
    
     /*
     * Οριοθετούμε την διεύθυνση στους 150 χαρακτήρες. Δεν υπάρχει λόγος να καταναλώσουμε παραπάνω πόρους.
     */
    @Size(max=150, message = "Address should not exceed 250 chars!")
    @Column(name="citizen_address", length=150, nullable=false, unique=false)
    private String citizenAddress = null;
    
    public Citizen() {}

    /*
     * Builder Pattern
     */
    public Citizen(CitizenBuilder aCitizen)
    {
        this.tautotita = aCitizen.tautotita;
        this.citizenName = aCitizen.citizenName;
        this.citizenSurname = aCitizen.citizenSurname;
        this.citizenGender = aCitizen.citizenGender;
        this.citizenDoB = aCitizen.citizenDoB;
        this.citizenAfm = aCitizen.citizenAfm;
        this.citizenAddress = aCitizen.citizenAddress;
    }
    
    public static class CitizenBuilder
    {
        private String tautotita = null;
        private String citizenName = null;
        private String citizenSurname = null;
        private String citizenGender;
        private String citizenDoB = null;
        private int citizenAfm;
        private String citizenAddress = null;

        public CitizenBuilder(String tautotita, String citizenName, String citizenSurname, String citizenGender, String citizenDoB)
        {
            this.tautotita = tautotita;
            this.citizenName = citizenName;
            this.citizenSurname = citizenSurname;
            this.citizenGender = citizenGender;
            this.citizenDoB = citizenDoB;
        }
        
        public CitizenBuilder CitizenAfm(int value) {
        	this.citizenAfm = value;
        	return this;
        }

        public CitizenBuilder CitizenAddress(String value) {
        	this.citizenAddress = value;
        	return this;
        }
        
        public Citizen build() {
        	return new Citizen(this);
        }
    }
    

    public String GetTautotita() {
        return this.tautotita;
    }


    public void SetTautotita(String tautotita) {
        this.tautotita = tautotita;
    }


    public String GetCitizenName() {
        return citizenName;
    }


    public void SetCitizenName(String citizenName) {
        this.citizenName = citizenName;
    }


    public String GetCitizenSurname() {
        return citizenSurname;
    }


    public void SetCitizenSurname(String citizenSurname) {
        this.citizenSurname = citizenSurname;
    }


    public String GetCitizenGender() {
        return citizenGender;
    }


    public void SetCitizenGender(String citizenGender) {
        this.citizenGender = citizenGender;
    }


    public String GetCitizenDoB() {
        return citizenDoB;
    }


    public void SetCitizenDoB(String citizenDoB) {
        this.citizenDoB = citizenDoB;
    }


    public int GetCitizenAfm() {
        return citizenAfm;
    }


    public void SetCitizenAfm(int citizenAfm) {
        this.citizenAfm = citizenAfm;
    }


    public String GetCitizenAddress() {
        return citizenAddress;
    }


    public void SetCitizenAddress(String citizenAddress) {
        this.citizenAddress = citizenAddress;
    }


    @Override
    public String toString() {
        return "Πολίτης [Ταυτότητα=" + tautotita + ", CitizenName=" + citizenName + ", CitizenSurname="
                + citizenSurname + ", CitizenGender=" + citizenGender + ", CitizenDoB=" + citizenDoB + ", CitizenAfm="
                + citizenAfm + ", CitizenAddress=" + citizenAddress + "]";
    }




    @Override
	public int hashCode() {
		return Objects.hash(citizenAddress, citizenAfm, citizenDoB, citizenGender, citizenName, citizenSurname,
				tautotita);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Citizen other = (Citizen) obj;
		return Objects.equals(citizenAddress, other.citizenAddress) && citizenAfm == other.citizenAfm
				&& Objects.equals(citizenDoB, other.citizenDoB) && Objects.equals(citizenGender, other.citizenGender)
				&& Objects.equals(citizenName, other.citizenName)
				&& Objects.equals(citizenSurname, other.citizenSurname) && Objects.equals(tautotita, other.tautotita);
	}

}
