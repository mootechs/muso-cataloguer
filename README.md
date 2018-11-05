# MuSO Cataloguer

This program is a Java app that generates RDF documents based on the ARC
Schema standards. In addition to ARC Schema, the program also contains
fields for entering in information based on the MuSO metadata standards. 
All ARC Schema fields are compulsory to complete, but MuSO Standard fields
are optional.


## Getting Started

Complete the fields in the program and click run when all desired fields
are filled. The lists discipline, genre, and notation all allow multiple
item selection, while the list license only allows one. Below is a list of information
pertaining to the fields for each standard.

## Limitations

Current version is untested on platforms other than Windows 10 x64.

## Usage

Must have Java SE Version 8 installed (SE 1.8.0). Will not work with Java 9 and above.

### Windows and Linux:

Open command prompt, navigate to the jar file and do:

```
java -jar muso_cataloguer.jar
```

### Mac OSX:

Will only work on x64 machines because SWT x86 libraries are deprecated. Do:

``` 
java -jar -XstartOnFirstThread muso_cataloguer.jar
```

##ARC Schema

	 <collex:archive>

    A shorthand reference to the contributing project or journal, one word such as "rossetti" or "rc_praxis". 
	This word should be unique to this particular set of content. You shouldn't, therefore, choose a reference like "PodunkUP" 
	if Podunk University Press intends to contribute a different set of content in future. (Instead, choose "podunk_up_journal1").
	You may use a wide variety of characters to form this name, but it is recommended that you use only lower case letters,
	numbers, and the underscore. 
	
	 <dc:title>

    the title of the object 
	
	 <dc:type>

    adapted from the DCMI list of types, this term should describe the medium, or format of the object. 
	
	 <role:***>

    individuals or organizations involved in the creation of the object. 

	 <collex:discipline>

    information about the disciplines that may be interested in the object 
	
	 <dc:date>

    date of the object -- may contain either a four digit year AND NOTHING ELSE
	
	 <collex:federation>

    The federation that this object belongs to. Currently, the legal values of this are 

    MESA -- medieval era; must be peer-reviewed by MESA
    REKN -- circa 1450 to 1700, inclusive; accepts all scholarly community's peer review decisions;
    NEAR -- American Literature, all dates; must be peer-reviewed by NEAR
    18thConnect -- 1660-1830, inclusive; accepts all scholarly community's peer review decisions;
    NINES -- 1785-1920, inclusive; accepts all scholarly community's peer review decisions;
    ModNets -- 1900 on, inclusive; accepts all scholarly community's peer review decisions;
    SiRO -- Studies in Radicalism; must be peer-reviewed by SiRO
    estc -- English Short Title Catalog; closed
    GLA -- Great Lakes Project; closed 
	
###MuSO Metadata Standards

	<muso:created>
    Element used when contributor wants to preserve a date other than that of a 
	particular object (i.e. performance date, date of revision or edition, etc.), 
	it has two child elements.
	<muso:subgenre>
    Information about music-specific genres for musical objects
    Vocabulary: https://www.loc.gov/catdir/cpso/lcmlalist.pdf
	
	<muso:other_id>
    Other relevant reference identifiers for the items that are known to the scholarly community and critical for discoverability 
	(i.e. Digital Object Identifier, Short Title Catalog, composer work number, etc.).
	
	<muso:notation>
    Reference to the notation types used on a musical object, 
	vocabulary based on the values in the Resource Description and Access Registry for Form of Musical Notation.
	
	<muso:rism>
    A shorthand reference to the contributing library or archive as assigned by Répertoire International des Sources Musicales (RISM)
    Vocabulary: http://www.rism.info/sigla
	
	<muso:license>
    Statement of the license for the surrogate object
	
	<muso:uniform_title>
    The uniform title of an object. Drawn from the Library of Congress Authorities.
    Vocabulary: https://authorities.loc.gov/