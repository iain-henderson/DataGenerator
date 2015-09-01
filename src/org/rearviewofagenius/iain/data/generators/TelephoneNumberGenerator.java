package org.rearviewofagenius.iain.data.generators;

public class TelephoneNumberGenerator extends Generator {

	Region region = Region.NORTH_AMERICA;

	boolean international = false;

	public TelephoneNumberGenerator(Integer rows, String region, Boolean international)
	{
		super(Integer.valueOf(queueDepth), rows);
		if(!this.region.countryCallingCode.equalsIgnoreCase(region) && 
				!this.region.name().equalsIgnoreCase(region) &&
				!this.region.toString().equalsIgnoreCase(region)){
			for(Region r : Region.values()){
				if(r.name().equalsIgnoreCase(region) || 
						r.toString().equalsIgnoreCase(region) ||
						r.countryCallingCode.equalsIgnoreCase(region)){
					this.region = r;
					unique = true;
				}
			}
		}
		if (this.international != international){
			this.international = international;
			unique = true;
		}
	}

	public TelephoneNumberGenerator(Integer rows, Integer region, Boolean international)
	{
		this(rows, region.toString(), international);
	}

	public TelephoneNumberGenerator(Integer rows, String region)
	{
		this(rows, region, Boolean.valueOf(false));
	}

	public TelephoneNumberGenerator(Integer rows, Integer region)
	{
		this(rows, region, Boolean.valueOf(false));
	}

	public TelephoneNumberGenerator(Integer rows, Boolean international)
	{
		this(rows, "1", international);
	}

	public TelephoneNumberGenerator(Integer rows)
	{
		this(rows, "1", Boolean.valueOf(false));
	}

	public String generate()
	{
		String number = "";
		switch (region.countryCallingCode){
		case "1":
			number = northAmerican();
			break;
		case "44":
		case "44 1481":
		case "44 1534":
		case "44 1624":
			number = unitedKingdom();
			break;
		default:
			break;
		}
		return international ? '+' + region.countryCallingCode + '-' + number : number;
	}

	private String northAmerican(){
		StringBuilder number = new StringBuilder();

		number.append(nextInt(7) + 2);
		number.append(String.format("%02d-", nextInt(100)));

		number.append(nextInt(7) + 2);


		int x = nextInt(10);
		int y;
		if (x == 1){
			y = nextInt(9);
			if (y > 0){
				y++;
			}
		} else {
			y = nextInt(10);
		}
		number.append(x);
		number.append(y);

		number.append('-');

		number.append(String.format("%04d", nextInt(10000)));

		return number.toString();
	}

	private String unitedKingdom(){
		// based off of https://en.wikipedia.org/wiki/Telephone_numbers_in_the_United_Kingdom#Format
		// each case switches on the number of possibilities
		switch(nextInt(7)){
		case 0:
			switch(nextInt(6)){
			case 0:
				return String.format("(01%02d %02d) %05d", nextInt(100), nextInt(100), nextInt(100000));
			case 1:
				return String.format("(01%03d) %06d", nextInt(1000), nextInt(1000000));
			case 2:
				return String.format("(01%03d) %05d", nextInt(1000), nextInt(100000));
			case 3:
				return String.format("(01%d1) %03d %04d", nextInt(10), nextInt(1000), nextInt(10000));
			case 4:
				return String.format("(011%d) %03d %04d", nextInt(10), nextInt(1000), nextInt(10000));
			case 5:
				return String.format("(0169 77) %04d", nextInt(10000));
			}
		case 1:
			return String.format("(02%d) %04d %04d", nextInt(10), nextInt(10000), nextInt(10000));
		case 2:
			return String.format("03%02d %03d %04d", nextInt(100), nextInt(1000), nextInt(10000));
		case 3:
			switch(nextInt(2)){
			case 0:
				return String.format("05%d %04d %04d", nextInt(2) + 5, nextInt(10000), nextInt(10000));
			case 1:
				return String.format("0500 %06d", nextInt(1000000));
			}
		case 4:
			switch(nextInt(4)){
			case 0:
				return String.format("070 %04d %04d", nextInt(10000), nextInt(10000));
			case 1:
				return String.format("07624 %06d", nextInt(1000000));
			case 2:
				return String.format("076 %04d %04d", nextInt(10000), nextInt(10000));
			case 3:
				return String.format("07%03d %05d", nextInt(1000), nextInt(100000));
			}
		case 5:
			switch(nextInt(4)){
			case 0:
				return String.format("08%02d %03d %04d", nextInt(100), nextInt(1000), nextInt(10000));
			case 1:
				return String.format("0800 %06d", nextInt(1000000));
			case 2:
				return String.format("086 %04d %04d", nextInt(10000), nextInt(10000));
			case 3:
				return "0800 1111";
			case 4:
				return String.format("0845 46 4%d", nextInt(10));
			}
		case 6:
			return String.format("09%02d %03d %04d", nextInt(100), nextInt(1000), nextInt(10000));
		}
		return "no number generated";
	}

	public String toString(){
		return "Telephone Generator";
	}
	private enum Region {
		NORTH_AMERICA("1"),
		EGYPT("20"),
		SOUTH_SUDAN("211"),
		MOROCCO("212"),
		ALGERIA("213"),
		LIECHTENSTEIN("423"),
		UNITED_KINGDOM("44"),
		GUERNSEY("44 1481"),
		JERSEY("44 1534"),
		ISLE_OF_MAN("44 1624"),
		GERMANY("49");
		private final String countryCallingCode;

		Region(String countryCallingCode){
			this.countryCallingCode = countryCallingCode;
		}
		
		public String toString(){
			return this.name().replaceAll("_", " ");
		}
	}
}
