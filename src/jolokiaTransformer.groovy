/**
 * @author David Turanski
 *
 * */
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

class ChannelMetrics {
    String stream
    String module
    String channel
    Map metrics
}

slurper = new JsonSlurper()
builder = new groovy.json.JsonBuilder()

jolokiaMetrics = slurper.parseText(payload)

channelMetrics = []

jolokiaMetrics.each { k, v ->
    (name, module, channel) = k.split(",")
     module = module.replaceAll('module=','')
     stream = module.substring(0,module.indexOf('.'))
     module = module.substring(module.indexOf('.')+1)
     channel = channel.replaceAll('name=','')
     channelMetrics <<  new ChannelMetrics(stream:stream, module:module, channel:channel,metrics:v)
}

result = builder channelMetrics

//return value
builder.toString()
