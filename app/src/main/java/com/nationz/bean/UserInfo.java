package com.nationz.bean;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * �û���Ϣ,��������֤��ʱʹ��
 * 
 * @author WuSong
 * @Date @2015-1-21
 */
public class UserInfo implements KvmSerializable, Serializable {
	private static final long serialVersionUID = 6224773662016085885L;
	/** �û���� ���� */
	protected String userName;
	/** �û����� */
	protected String userSurname;
	protected String userSerialnumber;
	/** �û����� ���� */
	protected String userEmail;
	/** RA�˻���o ���� */
	protected String userOrganization;
	/** RA�˻���OU ���� */
	protected String userOrgunit;
	/** �û���� */
	protected String userCountry;
	/** �û�ʡ�� */
	protected String userState;
	/** �û����� */
	protected String userLocality;
	/** �û��ֵ� */
	protected String userStreet;
	/** �û���Dns */
	protected String userDns;
	/** �û���Ip��ַ */
	protected String userIp;
	/** �û����� */
	protected String userTitle;
	/** �û����� */
	protected String userDescription;
	/** �û���ע1 �û������չ�ֶ� */
	protected String userAdditionalField1;
	/** �û���ע2 �û������չ�ֶ� */
	protected String userAdditionalField2;
	/** �û���ע3 �û������չ�ֶ� */
	protected String userAdditionalField3;
	/** �û���ע4 �û������չ�ֶ� */
	protected String userAdditionalField4;
	/** �û���ע5 �û������չ�ֶ� */
	protected String userAdditionalField5;
	/** �û���ע6�û������չ�ֶ� */
	protected String userAdditionalField6;
	/** �û���ע7 �û������չ�ֶ� */
	protected String userAdditionalField7;
	/** �û���ע8 �û������չ�ֶ� */
	protected String userAdditionalField8;
	/** �û���ע9 �û������չ�ֶ� */
	protected String userAdditionalField9;
	/** �û���ע10 �û������չ�ֶ� */
	protected String userAdditionalField10;

	public static Class<UserInfo> UserInfo_CLASS = UserInfo.class;

	@Override
	public Object getProperty(int index) {
		Object object = null;
		switch (index) {
		case 0: {
			object = this.userName;
			break;
		}
		case 1: {
			object = this.userSurname;
			break;
		}
		case 2: {
			object = this.userSerialnumber;
			break;
		}
		case 3: {
			object = this.userEmail;
			break;
		}
		case 4: {
			object = this.userOrganization;
			break;
		}
		case 5: {
			object = this.userOrgunit;
			break;
		}
		case 6: {
			object = this.userCountry;
			break;
		}
		case 7: {
			object = this.userState;
			break;
		}
		case 8: {
			object = this.userLocality;
			break;
		}
		case 9: {
			object = this.userStreet;
			break;
		}
		case 10: {
			object = this.userDns;
			break;
		}
		case 11: {
			object = this.userIp;
			break;
		}
		case 12: {
			object = this.userTitle;
			break;
		}
		case 13: {
			object = this.userDescription;
			break;
		}
		case 14: {
			object = this.userAdditionalField1;
			break;
		}
		case 15: {
			object = this.userAdditionalField2;
			break;
		}
		case 16: {
			object = this.userAdditionalField3;
			break;
		}
		case 17: {
			object = this.userAdditionalField4;
			break;
		}
		case 18: {
			object = this.userAdditionalField5;
			break;
		}
		case 19: {
			object = this.userAdditionalField6;
			break;
		}
		case 20: {
			object = this.userAdditionalField7;
			break;
		}
		case 21: {
			object = this.userAdditionalField8;
			break;
		}
		case 22: {
			object = this.userAdditionalField9;
			break;
		}
		case 23: {
			object = this.userAdditionalField10;
			break;
		}

		}
		return object;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 24;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getPropertyInfo(int index, Hashtable arg1,
			PropertyInfo propertyInfo) {
		switch (index) {
		case 0: {
			propertyInfo.name = "userName";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 1: {
			propertyInfo.name = "userSurname";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 2: {
			propertyInfo.name = "userSerialnumber";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 3: {
			propertyInfo.name = "userEmail";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 4: {
			propertyInfo.name = "userOrganization";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 5: {
			propertyInfo.name = "userOrgunit";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 6: {
			propertyInfo.name = "userCountry";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 7: {
			propertyInfo.name = "userState";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 8: {
			propertyInfo.name = "userLocality";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 9: {
			propertyInfo.name = "userStreet";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 10: {
			propertyInfo.name = "userDns";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 11: {
			propertyInfo.name = "userIp";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 12: {
			propertyInfo.name = "userTitle";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 13: {
			propertyInfo.name = "userDescription";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 14: {
			propertyInfo.name = "userAdditionalField1";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 15: {
			propertyInfo.name = "userAdditionalField2";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 16: {
			propertyInfo.name = "userAdditionalField3";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 17: {
			propertyInfo.name = "userAdditionalField4";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 18: {
			propertyInfo.name = "userAdditionalField5";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 19: {
			propertyInfo.name = "userAdditionalField6";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 20: {
			propertyInfo.name = "userAdditionalField7";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 21: {
			propertyInfo.name = "userAdditionalField8";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 22: {
			propertyInfo.name = "userAdditionalField9";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}
		case 23: {
			propertyInfo.name = "userAdditionalField10";
			propertyInfo.type = PropertyInfo.STRING_CLASS;
			break;
		}

		}

	}

	/**
	 * Ϊ���ֶθ���
	 * 
	 * @param index
	 *            �ֶε������ ����:0 ���userName, 1 ���userSurname,�Դ�����
	 * @param obj
	 *            �ֶε�ֵ
	 */
	@Override
	public void setProperty(int index, Object obj) {
		switch (index) {
		case 0: {
			this.userName = obj.toString();
			break;
		}
		case 1: {
			this.userSurname = obj.toString();
			break;
		}
		case 2: {
			this.userSerialnumber = obj.toString();
			break;
		}
		case 3: {
			this.userEmail = obj.toString();
			break;
		}
		case 4: {
			this.userOrganization = obj.toString();
			break;
		}
		case 5: {
			this.userOrgunit = obj.toString();
			break;
		}
		case 6: {
			this.userCountry = obj.toString();
			break;
		}
		case 7: {
			this.userState = obj.toString();
			break;
		}
		case 8: {
			this.userLocality = obj.toString();
			break;
		}
		case 9: {
			this.userStreet = obj.toString();
			break;
		}
		case 10: {
			this.userDns = obj.toString();
			break;
		}
		case 11: {
			this.userIp = obj.toString();
			break;
		}
		case 12: {
			this.userTitle = obj.toString();
			break;
		}
		case 13: {
			this.userDescription = obj.toString();
			break;
		}
		case 14: {
			this.userAdditionalField1 = obj.toString();
			break;
		}
		case 15: {
			this.userAdditionalField2 = obj.toString();
			break;
		}
		case 16: {
			this.userAdditionalField3 = obj.toString();
			break;
		}
		case 17: {
			this.userAdditionalField4 = obj.toString();
			break;
		}
		case 18: {
			this.userAdditionalField5 = obj.toString();
			break;
		}
		case 19: {
			this.userAdditionalField6 = obj.toString();
			break;
		}
		case 20: {
			this.userAdditionalField7 = obj.toString();
			break;
		}
		case 21: {
			this.userAdditionalField8 = obj.toString();
			break;
		}
		case 22: {
			this.userAdditionalField9 = obj.toString();
			break;
		}
		case 23: {
			this.userAdditionalField10 = obj.toString();
			break;
		}
		}

	}

	/**
	 * Gets the value of the userName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the value of the userName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserName(String value) {
		this.userName = value;
	}

	/**
	 * Gets the value of the userSurname property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserSurname() {
		return userSurname;
	}

	/**
	 * Sets the value of the userSurname property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserSurname(String value) {
		this.userSurname = value;
	}

	/**
	 * Gets the value of the userSerialnumber property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserSerialnumber() {
		return userSerialnumber;
	}

	/**
	 * Sets the value of the userSerialnumber property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserSerialnumber(String value) {
		this.userSerialnumber = value;
	}

	/**
	 * Gets the value of the userEmail property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * Sets the value of the userEmail property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserEmail(String value) {
		this.userEmail = value;
	}

	/**
	 * Gets the value of the userOrganization property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserOrganization() {
		return userOrganization;
	}

	/**
	 * Sets the value of the userOrganization property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserOrganization(String value) {
		this.userOrganization = value;
	}

	/**
	 * Gets the value of the userOrgunit property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserOrgunit() {
		return userOrgunit;
	}

	/**
	 * Sets the value of the userOrgunit property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserOrgunit(String value) {
		this.userOrgunit = value;
	}

	/**
	 * Gets the value of the userCountry property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserCountry() {
		return userCountry;
	}

	/**
	 * Sets the value of the userCountry property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserCountry(String value) {
		this.userCountry = value;
	}

	/**
	 * Gets the value of the userState property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserState() {
		return userState;
	}

	/**
	 * Sets the value of the userState property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserState(String value) {
		this.userState = value;
	}

	/**
	 * Gets the value of the userLocality property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserLocality() {
		return userLocality;
	}

	/**
	 * Sets the value of the userLocality property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserLocality(String value) {
		this.userLocality = value;
	}

	/**
	 * Gets the value of the userStreet property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserStreet() {
		return userStreet;
	}

	/**
	 * Sets the value of the userStreet property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserStreet(String value) {
		this.userStreet = value;
	}

	/**
	 * Gets the value of the userDns property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserDns() {
		return userDns;
	}

	/**
	 * Sets the value of the userDns property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserDns(String value) {
		this.userDns = value;
	}

	/**
	 * Gets the value of the userIp property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserIp() {
		return userIp;
	}

	/**
	 * Sets the value of the userIp property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserIp(String value) {
		this.userIp = value;
	}

	/**
	 * Gets the value of the userTitle property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserTitle() {
		return userTitle;
	}

	/**
	 * Sets the value of the userTitle property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserTitle(String value) {
		this.userTitle = value;
	}

	/**
	 * Gets the value of the userDescription property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserDescription() {
		return userDescription;
	}

	/**
	 * Sets the value of the userDescription property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserDescription(String value) {
		this.userDescription = value;
	}

	/**
	 * Gets the value of the userAdditionalField1 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserAdditionalField1() {
		return userAdditionalField1;
	}

	/**
	 * Sets the value of the userAdditionalField1 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserAdditionalField1(String value) {
		this.userAdditionalField1 = value;
	}

	/**
	 * Gets the value of the userAdditionalField2 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserAdditionalField2() {
		return userAdditionalField2;
	}

	/**
	 * Sets the value of the userAdditionalField2 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserAdditionalField2(String value) {
		this.userAdditionalField2 = value;
	}

	/**
	 * Gets the value of the userAdditionalField3 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserAdditionalField3() {
		return userAdditionalField3;
	}

	/**
	 * Sets the value of the userAdditionalField3 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserAdditionalField3(String value) {
		this.userAdditionalField3 = value;
	}

	/**
	 * Gets the value of the userAdditionalField4 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserAdditionalField4() {
		return userAdditionalField4;
	}

	/**
	 * Sets the value of the userAdditionalField4 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserAdditionalField4(String value) {
		this.userAdditionalField4 = value;
	}

	/**
	 * Gets the value of the userAdditionalField5 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserAdditionalField5() {
		return userAdditionalField5;
	}

	/**
	 * Sets the value of the userAdditionalField5 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserAdditionalField5(String value) {
		this.userAdditionalField5 = value;
	}

	/**
	 * Gets the value of the userAdditionalField6 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserAdditionalField6() {
		return userAdditionalField6;
	}

	/**
	 * Sets the value of the userAdditionalField6 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserAdditionalField6(String value) {
		this.userAdditionalField6 = value;
	}

	/**
	 * Gets the value of the userAdditionalField7 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserAdditionalField7() {
		return userAdditionalField7;
	}

	/**
	 * Sets the value of the userAdditionalField7 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserAdditionalField7(String value) {
		this.userAdditionalField7 = value;
	}

	/**
	 * Gets the value of the userAdditionalField8 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserAdditionalField8() {
		return userAdditionalField8;
	}

	/**
	 * Sets the value of the userAdditionalField8 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserAdditionalField8(String value) {
		this.userAdditionalField8 = value;
	}

	/**
	 * Gets the value of the userAdditionalField9 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserAdditionalField9() {
		return userAdditionalField9;
	}

	/**
	 * Sets the value of the userAdditionalField9 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserAdditionalField9(String value) {
		this.userAdditionalField9 = value;
	}

	/**
	 * Gets the value of the userAdditionalField10 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserAdditionalField10() {
		return userAdditionalField10;
	}

	/**
	 * Sets the value of the userAdditionalField10 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserAdditionalField10(String value) {
		this.userAdditionalField10 = value;
	}

}
