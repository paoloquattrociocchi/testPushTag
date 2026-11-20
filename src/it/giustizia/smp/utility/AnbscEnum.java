package it.giustizia.smp.utility;

public enum AnbscEnum {
	
	ANBSC_SEQUESTRO						(1L,"01"),
	ANBSC_REVOCA_SEQUESTRO				(2L,"02"),
	ANBSC_CONFISCA_I_GRADO				(3L,"03"),
	ANBSC_CONFISCA_II_GRADO				(4L,"04"),
	ANBSC_STRALCIO						(5L,"05"),
	ANBSC_RIUNIONE						(6L,"06"),
	ANBSC_CONFISCA_DEFINITIVA_CASSAZIONE(7L,"07"),
	ANBSC_REVOCA_CONFISCA				(8L,"08"),
	ANBSC_SEQUESTRO_URGENTE				(13L,"13"),
	ANBSC_SEQUESTRO_ANTICIPATO			(14L,"14"),
	ANBSC_CONFISCA_DEFINITIVA_I_GRADO	(15L,"15"),
	ANBSC_CONFISCA_DEFINITIVA_II_GRADO	(16L,"16"),
	ANBSC_REVOCA_SEQUESTRO_I_GRADO		(17L,"17"),
	ANBSC_REVOCA_SEQUESTRO_II_GRADO		(18L,"18");
	
	private Long id;
	private String key;
	
	private AnbscEnum(Long id, String key) {
		this.id = id;
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public Long getId() {
		return id;
	}

	/**
	 * Ritorna il valore di default
	 * @param key
	 * @return
	 */
	public static AnbscEnum getById(Long id) {
		for(AnbscEnum anbscEnum : AnbscEnum.values()) {
			if(anbscEnum.getId().equals(id)) {
				return anbscEnum;
			}
		}
		return null;
	}
}
