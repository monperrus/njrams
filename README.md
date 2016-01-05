njrams is a toolkit for analyzing Ngrams of Java tokens.

Compiling: 
---------

    ## downloading the dependencies
    wget http://sachaproject.gforge.inria.fr/lib-njrams.jar
    mkdir bin
    ## compiling 
    javac -d bin -cp lib-njrams.jar `find src -name "*java"`

`lib-njrams.jar` is a dump of commons-io-2.3.jar org.eclipse.core.runtime_3.7.0.v20110110.jar
commons-lang-2.6.jar org.eclipse.equinox.common_3.6.0.v20110523.jar
org.eclipse.core.contenttype_3.4.100.v20110423-0524.jar  org.eclipse.equinox.preferences_3.4.1.R37x_v20110725.jar
org.eclipse.core.jobs_3.5.100.v20110404.jar org.eclipse.jdt.core_3.7.1.v_B76_R37x.jar
org.eclipse.core.resources_3.7.100.v20110510-0712.jar org.eclipse.osgi_3.7.1.R37x_v20110808-1106.jar

Running:
------
    
    njrams.Main1 outputs the n-grams of a given size and type
    java njrams.Main1 <source dir> <ngram size> <tokenizer type>
    <tokenizer type>: njrams.FullNgram njrams.RenameIdentifierNgram njrams.RenameLiteralNgram njrams.RenameIdentifierLiteralNgram njrams.TokenTypeNgram njrams.BinaryNgram 
    Example: 
    java njrams.Main1 src 3 njrams.FullNgram
    
    njrams.Main2 outputs the main statistics of n-grams of a given size for all known n-gram types
    java njrams.Main2 <source dir> <ngram size>
    Example: 
    java njrams.Main2 src 5
    
    njrams.Main3 generates a scilab file that itself creates a figure
    The file summarizes the number of ngrams per size and type 
    java njrams.Main3 <source dir>
    Example: 
    java njrams.Main3 src

