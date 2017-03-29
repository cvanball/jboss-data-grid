package com.redhat.jdg.pojo;
/**
* Maps a relational database table Contract to a java object, Contract
*
* 
*
* @author	ReverseEngineer
*/
import java.io.Serializable;
import org.infinispan.protostream.annotations.ProtoField;

public class Product implements Serializable {


	
	public java.lang.Long itemid;

	public java.lang.String name;
	
	public java.lang.String description;
	
	public java.lang.Double price;


	@ProtoField(number = 1, required = false)
	public java.lang.Long getItemid( ) { 
		return this.itemid;
	}

	public void setItemid( java.lang.Long Itemid) { 
		 this.itemid = Itemid; 
	}

	@ProtoField(number = 2, required = true)
	public java.lang.String getName( ) { 
		return this.name;
	}

	public void setName( java.lang.String Name) { 
		 this.name = Name; 
	}

	@ProtoField(number = 3, required = false)
	public java.lang.String getDescription( ) { 
		return this.description;
	}

	public void setDescription( java.lang.String Description) { 
		 this.description = Description; 
	}

	@ProtoField(number = 4, required = true)
	public java.lang.Double getPrice( ) { 
		return this.price;
	}

	public void setPrice( java.lang.Double Price) { 
		 this.price = Price; 
	}


	public String toString()  {
		StringBuffer output = new StringBuffer();
		output.append("Itemid:");
		output.append(getItemid());
		output.append("\n");
		output.append("Name:");
		output.append(getName());
		output.append("\n");
		output.append("Description:");
		output.append(getDescription());
		output.append("\n");
		output.append("Price:");
		output.append(getPrice());
		output.append("\n");
		return output.toString();
	}

} // class Contract
