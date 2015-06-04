/** -------------------- CONSTANTS --------------------**/
COLORIZE_BY_LABEL_CLASS = "labelClass"
COLORIZE_BY_STYLE = "style"

/** -------------------- CONFIG --------------------**/
COLORIZE_VARIANT = COLORIZE_BY_STYLE

def issueLinkManager = componentManager.getIssueLinkManager()
def valueList = issueLinkManager.getOutwardLinks(issue.id)*.getDestinationObject()*.getKey()
valueList += issueLinkManager.getInwardLinks(issue.id)*.getDestinationObject()*.getKey()
def valueSet = valueList.toSet()
/** -------------------- GENERATE HTML --------------------**/
def result = ""
valueSet.each{
    result += getHTML(it)
}
return result

/** -------------------- METHODS --------------------**/

def getHTML(String text){
    if(COLORIZE_VARIANT == COLORIZE_BY_STYLE){
    	def colour = getColor(text)
        return "<span class='aui-label ghx-label-1' style='background: " + colour + "; border-color: " + colour + ";' title='" + text + "' >" + text + "</span>"
    } 
    
    def labelClass = getLabelClass(text);
    return "<span class='aui-label " + labelClass + "' title='" + text + "' >" + text + "</span>"
}

def getColor(String text){
    def hash = text.hashCode();
    def hue = hash*10;
    return 'hsl(' + hue + ', 50%, 50.0%)';
}

def getLabelClass(String text){
    def hash = text.hashCode();
    def label = 1 + hash % 9  
    return "ghx-label-" + label
}
