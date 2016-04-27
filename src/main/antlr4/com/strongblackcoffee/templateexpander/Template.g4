grammar Template;

template: ( text | replaceable | unexpected_rc)* ;

replaceable: LC key ( PIPE transformation ( COLON targ )* )*  RC ;
key:            ALPHA ( ALPHA | DIGIT | UNDER | DOT )* ;
transformation: ALPHA ( ALPHA | DIGIT | UNDER | DOT )* ;
targ: plainarg | QUOTE quotedarg QUOTE ;
plainarg: ( ALPHA | DIGIT | UNDER | DOT | PLAIN )+ ;
quotedarg: ( LC | RC | EBS | EQUOTE | ALPHA | DIGIT | UNDER | DOT | PLAIN | WS | PIPE | COLON | OTHER )* ;
unexpected_rc : RC ;
text: (ALPHA | DIGIT | UNDER | DOT | WS | PIPE | COLON | QUOTE | PLAIN | OTHER)+ ;


LC:    '{' ;
RC:    '}' ;
EBS:    '\\\\' { setText("\\"); } ;
EQUOTE: '\\"'  { setText("\""); } ;

ALPHA: [a-zA-Z] ;
DIGIT: [0-9] ;
UNDER: '_' ;
DOT:   '.' ;

PLAIN: [-+/*=@#$%^&()\[\];,<>~] ;

WS:    ' ' | '\t' ;
PIPE:  '|' ;
COLON: ':' ;
QUOTE: '"' ;
OTHER: .   ; // anything that didn't match an earlier rule