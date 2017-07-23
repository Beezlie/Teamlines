from configparser import ConfigParser

#get authentication info from .ini file
def config(section, filename='authentication.ini'):
    #create a parser
    parser = ConfigParser()
    #read config file
    parser.read(filename)

    #get section
    vals = {}
    if parser.has_section(section):
        params = parser.items(section)
        for param in params:
            vals[param[0]] = param[1]
    else:
        raise Exception('Selection {0} not found in the {1} file'.format(section,filename))

    return vals