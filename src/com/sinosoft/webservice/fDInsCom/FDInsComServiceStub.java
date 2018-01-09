
/**
 * FDInsComServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
        package com.sinosoft.webservice.fDInsCom;

        

        /*
        *  FDInsComServiceStub java implementation
        */

        
        public class FDInsComServiceStub extends org.apache.axis2.client.Stub
        {
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();

        private static int counter = 0;

        private static synchronized java.lang.String getUniqueSuffix(){
            // reset the counter if it is greater than 99999
            if (counter > 99999){
                counter = 0;
            }
            counter = counter + 1; 
            return java.lang.Long.toString(java.lang.System.currentTimeMillis()) + "_" + counter;
        }

    
    private void populateAxisService() throws org.apache.axis2.AxisFault {

     //creating the Service with a unique name
     _service = new org.apache.axis2.description.AxisService("FDInsComService" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[1];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://service.webService.ebusiness.sinosoft.com", "getFDInsCom"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         


    }

    /**
      *Constructor that takes in a configContext
      */

    public FDInsComServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public FDInsComServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint, boolean useSeparateListener)
        throws org.apache.axis2.AxisFault {
         //To populate AxisService
         populateAxisService();
         populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,_service);
        
	
        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
        
            //Set the soap version
            _serviceClient.getOptions().setSoapVersionURI(org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
        
    
    }

    /**
     * Default Constructor
     */
    public FDInsComServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"http://localhost:8081/product/services/FDInsComService.FDInsComServiceHttpSoap12Endpoint/" );
                
    }

    /**
     * Default Constructor
     */
    public FDInsComServiceStub() throws org.apache.axis2.AxisFault {
        
                    this("http://localhost:8081/product/services/FDInsComService.FDInsComServiceHttpSoap12Endpoint/" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public FDInsComServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }



        
                    /**
                     * Auto generated method signature
                     * 
                     * @see com.sinosoft.webservice.fDInsCom.FDInsComService#getFDInsCom
                     * @param getFDInsCom0
                    
                     */

                    

                            public  com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsComResponse getFDInsCom(

                            com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsCom getFDInsCom0)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("urn:getFDInsCom");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getFDInsCom0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://service.webService.ebusiness.sinosoft.com",
                                                    "getFDInsCom")), new javax.xml.namespace.QName("http://service.webService.ebusiness.sinosoft.com",
                                                    "getFDInsCom"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsComResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsComResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getFDInsCom"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getFDInsCom"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getFDInsCom"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * 
                * @see com.sinosoft.webservice.fDInsCom.FDInsComService#startgetFDInsCom
                    * @param getFDInsCom0
                
                */
                public  void startgetFDInsCom(

                 com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsCom getFDInsCom0,

                  final com.sinosoft.webservice.fDInsCom.FDInsComServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
             _operationClient.getOptions().setAction("urn:getFDInsCom");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getFDInsCom0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://service.webService.ebusiness.sinosoft.com",
                                                    "getFDInsCom")), new javax.xml.namespace.QName("http://service.webService.ebusiness.sinosoft.com",
                                                    "getFDInsCom"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsComResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetFDInsCom(
                                        (com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsComResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetFDInsCom(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getFDInsCom"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getFDInsCom"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getFDInsCom"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetFDInsCom(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetFDInsCom(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetFDInsCom(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetFDInsCom(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetFDInsCom(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetFDInsCom(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetFDInsCom(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetFDInsCom(f);
                                            }
									    } else {
										    callback.receiveErrorgetFDInsCom(f);
									    }
									} else {
									    callback.receiveErrorgetFDInsCom(f);
									}
								} else {
								    callback.receiveErrorgetFDInsCom(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetFDInsCom(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[0].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[0].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                


       /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
       private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
       return returnMap;
    }

    
    
    private javax.xml.namespace.QName[] opNameArray = null;
    private boolean optimizeContent(javax.xml.namespace.QName opName) {
        

        if (opNameArray == null) {
            return false;
        }
        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;   
            }
        }
        return false;
    }
     //http://localhost:8081/product/services/FDInsComService.FDInsComServiceHttpSoap12Endpoint/
        public static class RequestExtension
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = RequestExtension
                Namespace URI = http://model.webService.ebusiness.sinosoft.com/xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for MaxRecords
                        */

                        
                                    protected java.lang.String localMaxRecords ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMaxRecordsTracker = false ;

                           public boolean isMaxRecordsSpecified(){
                               return localMaxRecordsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMaxRecords(){
                               return localMaxRecords;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MaxRecords
                               */
                               public void setMaxRecords(java.lang.String param){
                            localMaxRecordsTracker = true;
                                   
                                            this.localMaxRecords=param;
                                    

                               }
                            

                        /**
                        * field for OrderField
                        */

                        
                                    protected java.lang.String localOrderField ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOrderFieldTracker = false ;

                           public boolean isOrderFieldSpecified(){
                               return localOrderFieldTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOrderField(){
                               return localOrderField;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OrderField
                               */
                               public void setOrderField(java.lang.String param){
                            localOrderFieldTracker = true;
                                   
                                            this.localOrderField=param;
                                    

                               }
                            

                        /**
                        * field for OrderFlag
                        */

                        
                                    protected java.lang.String localOrderFlag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOrderFlagTracker = false ;

                           public boolean isOrderFlagSpecified(){
                               return localOrderFlagTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOrderFlag(){
                               return localOrderFlag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OrderFlag
                               */
                               public void setOrderFlag(java.lang.String param){
                            localOrderFlagTracker = true;
                                   
                                            this.localOrderFlag=param;
                                    

                               }
                            

                        /**
                        * field for PageFlag
                        */

                        
                                    protected java.lang.String localPageFlag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPageFlagTracker = false ;

                           public boolean isPageFlagSpecified(){
                               return localPageFlagTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPageFlag(){
                               return localPageFlag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PageFlag
                               */
                               public void setPageFlag(java.lang.String param){
                            localPageFlagTracker = true;
                                   
                                            this.localPageFlag=param;
                                    

                               }
                            

                        /**
                        * field for PageRowNum
                        */

                        
                                    protected java.lang.String localPageRowNum ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPageRowNumTracker = false ;

                           public boolean isPageRowNumSpecified(){
                               return localPageRowNumTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPageRowNum(){
                               return localPageRowNum;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PageRowNum
                               */
                               public void setPageRowNum(java.lang.String param){
                            localPageRowNumTracker = true;
                                   
                                            this.localPageRowNum=param;
                                    

                               }
                            

                        /**
                        * field for RowNumStart
                        */

                        
                                    protected java.lang.String localRowNumStart ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRowNumStartTracker = false ;

                           public boolean isRowNumStartSpecified(){
                               return localRowNumStartTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRowNumStart(){
                               return localRowNumStart;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RowNumStart
                               */
                               public void setRowNumStart(java.lang.String param){
                            localRowNumStartTracker = true;
                                   
                                            this.localRowNumStart=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://model.webService.ebusiness.sinosoft.com/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":RequestExtension",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "RequestExtension",
                           xmlWriter);
                   }

               
                   }
                if (localMaxRecordsTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "maxRecords", xmlWriter);
                             

                                          if (localMaxRecords==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMaxRecords);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOrderFieldTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "orderField", xmlWriter);
                             

                                          if (localOrderField==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOrderField);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOrderFlagTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "orderFlag", xmlWriter);
                             

                                          if (localOrderFlag==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOrderFlag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPageFlagTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "pageFlag", xmlWriter);
                             

                                          if (localPageFlag==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPageFlag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPageRowNumTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "pageRowNum", xmlWriter);
                             

                                          if (localPageRowNum==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPageRowNum);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRowNumStartTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "rowNumStart", xmlWriter);
                             

                                          if (localRowNumStart==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRowNumStart);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://model.webService.ebusiness.sinosoft.com/xsd")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localMaxRecordsTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "maxRecords"));
                                 
                                         elementList.add(localMaxRecords==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxRecords));
                                    } if (localOrderFieldTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "orderField"));
                                 
                                         elementList.add(localOrderField==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOrderField));
                                    } if (localOrderFlagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "orderFlag"));
                                 
                                         elementList.add(localOrderFlag==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOrderFlag));
                                    } if (localPageFlagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "pageFlag"));
                                 
                                         elementList.add(localPageFlag==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPageFlag));
                                    } if (localPageRowNumTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "pageRowNum"));
                                 
                                         elementList.add(localPageRowNum==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPageRowNum));
                                    } if (localRowNumStartTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "rowNumStart"));
                                 
                                         elementList.add(localRowNumStart==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRowNumStart));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static RequestExtension parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            RequestExtension object =
                new RequestExtension();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"RequestExtension".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (RequestExtension)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","maxRecords").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMaxRecords(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","orderField").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOrderField(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","orderFlag").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOrderFlag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","pageFlag").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPageFlag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","pageRowNum").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPageRowNum(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","rowNumStart").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRowNumStart(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class GetFDInsCom
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://service.webService.ebusiness.sinosoft.com",
                "getFDInsCom",
                "ns3");

            

                        /**
                        * field for FDInsComRequest
                        */

                        
                                    protected FDInsComRequest localFDInsComRequest ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFDInsComRequestTracker = false ;

                           public boolean isFDInsComRequestSpecified(){
                               return localFDInsComRequestTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return FDInsComRequest
                           */
                           public  FDInsComRequest getFDInsComRequest(){
                               return localFDInsComRequest;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FDInsComRequest
                               */
                               public void setFDInsComRequest(FDInsComRequest param){
                            localFDInsComRequestTracker = true;
                                   
                                            this.localFDInsComRequest=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME);
               return factory.createOMElement(dataSource,MY_QNAME);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://service.webService.ebusiness.sinosoft.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getFDInsCom",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getFDInsCom",
                           xmlWriter);
                   }

               
                   }
                if (localFDInsComRequestTracker){
                                    if (localFDInsComRequest==null){

                                        writeStartElement(null, "http://service.webService.ebusiness.sinosoft.com", "fDInsComRequest", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localFDInsComRequest.serialize(new javax.xml.namespace.QName("http://service.webService.ebusiness.sinosoft.com","fDInsComRequest"),
                                        xmlWriter);
                                    }
                                }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://service.webService.ebusiness.sinosoft.com")){
                return "ns3";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localFDInsComRequestTracker){
                            elementList.add(new javax.xml.namespace.QName("http://service.webService.ebusiness.sinosoft.com",
                                                                      "fDInsComRequest"));
                            
                            
                                    elementList.add(localFDInsComRequest==null?null:
                                    localFDInsComRequest);
                                }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static GetFDInsCom parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetFDInsCom object =
                new GetFDInsCom();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"getFDInsCom".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetFDInsCom)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.webService.ebusiness.sinosoft.com","fDInsComRequest").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setFDInsComRequest(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setFDInsComRequest(FDInsComRequest.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class FMClaimGuide
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = FMClaimGuide
                Namespace URI = http://pojo.webService.ebusiness.sinosoft.com/xsd
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for CGContent
                        */

                        
                                    protected java.lang.String localCGContent ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCGContentTracker = false ;

                           public boolean isCGContentSpecified(){
                               return localCGContentTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCGContent(){
                               return localCGContent;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CGContent
                               */
                               public void setCGContent(java.lang.String param){
                            localCGContentTracker = true;
                                   
                                            this.localCGContent=param;
                                    

                               }
                            

                        /**
                        * field for AddTime
                        */

                        
                                    protected java.lang.String localAddTime ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAddTimeTracker = false ;

                           public boolean isAddTimeSpecified(){
                               return localAddTimeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAddTime(){
                               return localAddTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AddTime
                               */
                               public void setAddTime(java.lang.String param){
                            localAddTimeTracker = true;
                                   
                                            this.localAddTime=param;
                                    

                               }
                            

                        /**
                        * field for AddUser
                        */

                        
                                    protected java.lang.String localAddUser ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAddUserTracker = false ;

                           public boolean isAddUserSpecified(){
                               return localAddUserTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAddUser(){
                               return localAddUser;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AddUser
                               */
                               public void setAddUser(java.lang.String param){
                            localAddUserTracker = true;
                                   
                                            this.localAddUser=param;
                                    

                               }
                            

                        /**
                        * field for Id
                        */

                        
                                    protected java.lang.String localId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIdTracker = false ;

                           public boolean isIdSpecified(){
                               return localIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getId(){
                               return localId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Id
                               */
                               public void setId(java.lang.String param){
                            localIdTracker = true;
                                   
                                            this.localId=param;
                                    

                               }
                            

                        /**
                        * field for ModifyTime
                        */

                        
                                    protected java.lang.String localModifyTime ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localModifyTimeTracker = false ;

                           public boolean isModifyTimeSpecified(){
                               return localModifyTimeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getModifyTime(){
                               return localModifyTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ModifyTime
                               */
                               public void setModifyTime(java.lang.String param){
                            localModifyTimeTracker = true;
                                   
                                            this.localModifyTime=param;
                                    

                               }
                            

                        /**
                        * field for ModifyUser
                        */

                        
                                    protected java.lang.String localModifyUser ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localModifyUserTracker = false ;

                           public boolean isModifyUserSpecified(){
                               return localModifyUserTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getModifyUser(){
                               return localModifyUser;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ModifyUser
                               */
                               public void setModifyUser(java.lang.String param){
                            localModifyUserTracker = true;
                                   
                                            this.localModifyUser=param;
                                    

                               }
                            

                        /**
                        * field for OrderFlag
                        */

                        
                                    protected java.lang.String localOrderFlag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOrderFlagTracker = false ;

                           public boolean isOrderFlagSpecified(){
                               return localOrderFlagTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOrderFlag(){
                               return localOrderFlag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OrderFlag
                               */
                               public void setOrderFlag(java.lang.String param){
                            localOrderFlagTracker = true;
                                   
                                            this.localOrderFlag=param;
                                    

                               }
                            

                        /**
                        * field for Prop1
                        */

                        
                                    protected java.lang.String localProp1 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProp1Tracker = false ;

                           public boolean isProp1Specified(){
                               return localProp1Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProp1(){
                               return localProp1;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Prop1
                               */
                               public void setProp1(java.lang.String param){
                            localProp1Tracker = true;
                                   
                                            this.localProp1=param;
                                    

                               }
                            

                        /**
                        * field for Prop2
                        */

                        
                                    protected java.lang.String localProp2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProp2Tracker = false ;

                           public boolean isProp2Specified(){
                               return localProp2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProp2(){
                               return localProp2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Prop2
                               */
                               public void setProp2(java.lang.String param){
                            localProp2Tracker = true;
                                   
                                            this.localProp2=param;
                                    

                               }
                            

                        /**
                        * field for Prop3
                        */

                        
                                    protected java.lang.String localProp3 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProp3Tracker = false ;

                           public boolean isProp3Specified(){
                               return localProp3Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProp3(){
                               return localProp3;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Prop3
                               */
                               public void setProp3(java.lang.String param){
                            localProp3Tracker = true;
                                   
                                            this.localProp3=param;
                                    

                               }
                            

                        /**
                        * field for Suppliercode
                        */

                        
                                    protected java.lang.String localSuppliercode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSuppliercodeTracker = false ;

                           public boolean isSuppliercodeSpecified(){
                               return localSuppliercodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSuppliercode(){
                               return localSuppliercode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Suppliercode
                               */
                               public void setSuppliercode(java.lang.String param){
                            localSuppliercodeTracker = true;
                                   
                                            this.localSuppliercode=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://pojo.webService.ebusiness.sinosoft.com/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":FMClaimGuide",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "FMClaimGuide",
                           xmlWriter);
                   }

               
                   }
                if (localCGContentTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "CGContent", xmlWriter);
                             

                                          if (localCGContent==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCGContent);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAddTimeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "addTime", xmlWriter);
                             

                                          if (localAddTime==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAddTime);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAddUserTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "addUser", xmlWriter);
                             

                                          if (localAddUser==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAddUser);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localIdTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "id", xmlWriter);
                             

                                          if (localId==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localModifyTimeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "modifyTime", xmlWriter);
                             

                                          if (localModifyTime==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localModifyTime);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localModifyUserTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "modifyUser", xmlWriter);
                             

                                          if (localModifyUser==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localModifyUser);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOrderFlagTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "orderFlag", xmlWriter);
                             

                                          if (localOrderFlag==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOrderFlag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localProp1Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "prop1", xmlWriter);
                             

                                          if (localProp1==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProp1);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localProp2Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "prop2", xmlWriter);
                             

                                          if (localProp2==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProp2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localProp3Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "prop3", xmlWriter);
                             

                                          if (localProp3==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProp3);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSuppliercodeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "suppliercode", xmlWriter);
                             

                                          if (localSuppliercode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSuppliercode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://pojo.webService.ebusiness.sinosoft.com/xsd")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localCGContentTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "CGContent"));
                                 
                                         elementList.add(localCGContent==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCGContent));
                                    } if (localAddTimeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "addTime"));
                                 
                                         elementList.add(localAddTime==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAddTime));
                                    } if (localAddUserTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "addUser"));
                                 
                                         elementList.add(localAddUser==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAddUser));
                                    } if (localIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "id"));
                                 
                                         elementList.add(localId==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId));
                                    } if (localModifyTimeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "modifyTime"));
                                 
                                         elementList.add(localModifyTime==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localModifyTime));
                                    } if (localModifyUserTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "modifyUser"));
                                 
                                         elementList.add(localModifyUser==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localModifyUser));
                                    } if (localOrderFlagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "orderFlag"));
                                 
                                         elementList.add(localOrderFlag==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOrderFlag));
                                    } if (localProp1Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "prop1"));
                                 
                                         elementList.add(localProp1==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProp1));
                                    } if (localProp2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "prop2"));
                                 
                                         elementList.add(localProp2==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProp2));
                                    } if (localProp3Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "prop3"));
                                 
                                         elementList.add(localProp3==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProp3));
                                    } if (localSuppliercodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "suppliercode"));
                                 
                                         elementList.add(localSuppliercode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSuppliercode));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static FMClaimGuide parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            FMClaimGuide object =
                new FMClaimGuide();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"FMClaimGuide".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (FMClaimGuide)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","CGContent").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCGContent(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","addTime").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAddTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","addUser").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAddUser(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","id").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","modifyTime").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setModifyTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","modifyUser").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setModifyUser(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","orderFlag").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOrderFlag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","prop1").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProp1(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","prop2").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProp2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","prop3").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProp3(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","suppliercode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSuppliercode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class FMClaimData
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = FMClaimData
                Namespace URI = http://pojo.webService.ebusiness.sinosoft.com/xsd
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for Content
                        */

                        
                                    protected java.lang.String localContent ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localContentTracker = false ;

                           public boolean isContentSpecified(){
                               return localContentTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getContent(){
                               return localContent;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Content
                               */
                               public void setContent(java.lang.String param){
                            localContentTracker = true;
                                   
                                            this.localContent=param;
                                    

                               }
                            

                        /**
                        * field for Id
                        */

                        
                                    protected java.lang.String localId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIdTracker = false ;

                           public boolean isIdSpecified(){
                               return localIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getId(){
                               return localId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Id
                               */
                               public void setId(java.lang.String param){
                            localIdTracker = true;
                                   
                                            this.localId=param;
                                    

                               }
                            

                        /**
                        * field for OrderFlag
                        */

                        
                                    protected java.lang.String localOrderFlag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOrderFlagTracker = false ;

                           public boolean isOrderFlagSpecified(){
                               return localOrderFlagTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOrderFlag(){
                               return localOrderFlag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OrderFlag
                               */
                               public void setOrderFlag(java.lang.String param){
                            localOrderFlagTracker = true;
                                   
                                            this.localOrderFlag=param;
                                    

                               }
                            

                        /**
                        * field for Prop1
                        */

                        
                                    protected java.lang.String localProp1 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProp1Tracker = false ;

                           public boolean isProp1Specified(){
                               return localProp1Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProp1(){
                               return localProp1;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Prop1
                               */
                               public void setProp1(java.lang.String param){
                            localProp1Tracker = true;
                                   
                                            this.localProp1=param;
                                    

                               }
                            

                        /**
                        * field for Prop2
                        */

                        
                                    protected java.lang.String localProp2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProp2Tracker = false ;

                           public boolean isProp2Specified(){
                               return localProp2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProp2(){
                               return localProp2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Prop2
                               */
                               public void setProp2(java.lang.String param){
                            localProp2Tracker = true;
                                   
                                            this.localProp2=param;
                                    

                               }
                            

                        /**
                        * field for Prop3
                        */

                        
                                    protected java.lang.String localProp3 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProp3Tracker = false ;

                           public boolean isProp3Specified(){
                               return localProp3Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProp3(){
                               return localProp3;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Prop3
                               */
                               public void setProp3(java.lang.String param){
                            localProp3Tracker = true;
                                   
                                            this.localProp3=param;
                                    

                               }
                            

                        /**
                        * field for TitleID
                        */

                        
                                    protected java.lang.String localTitleID ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTitleIDTracker = false ;

                           public boolean isTitleIDSpecified(){
                               return localTitleIDTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTitleID(){
                               return localTitleID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TitleID
                               */
                               public void setTitleID(java.lang.String param){
                            localTitleIDTracker = true;
                                   
                                            this.localTitleID=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://pojo.webService.ebusiness.sinosoft.com/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":FMClaimData",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "FMClaimData",
                           xmlWriter);
                   }

               
                   }
                if (localContentTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "content", xmlWriter);
                             

                                          if (localContent==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localContent);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localIdTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "id", xmlWriter);
                             

                                          if (localId==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOrderFlagTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "orderFlag", xmlWriter);
                             

                                          if (localOrderFlag==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOrderFlag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localProp1Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "prop1", xmlWriter);
                             

                                          if (localProp1==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProp1);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localProp2Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "prop2", xmlWriter);
                             

                                          if (localProp2==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProp2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localProp3Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "prop3", xmlWriter);
                             

                                          if (localProp3==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProp3);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTitleIDTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "titleID", xmlWriter);
                             

                                          if (localTitleID==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTitleID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://pojo.webService.ebusiness.sinosoft.com/xsd")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localContentTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "content"));
                                 
                                         elementList.add(localContent==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localContent));
                                    } if (localIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "id"));
                                 
                                         elementList.add(localId==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId));
                                    } if (localOrderFlagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "orderFlag"));
                                 
                                         elementList.add(localOrderFlag==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOrderFlag));
                                    } if (localProp1Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "prop1"));
                                 
                                         elementList.add(localProp1==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProp1));
                                    } if (localProp2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "prop2"));
                                 
                                         elementList.add(localProp2==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProp2));
                                    } if (localProp3Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "prop3"));
                                 
                                         elementList.add(localProp3==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProp3));
                                    } if (localTitleIDTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "titleID"));
                                 
                                         elementList.add(localTitleID==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTitleID));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static FMClaimData parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            FMClaimData object =
                new FMClaimData();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"FMClaimData".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (FMClaimData)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","content").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setContent(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","id").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","orderFlag").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOrderFlag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","prop1").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProp1(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","prop2").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProp2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","prop3").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProp3(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","titleID").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTitleID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class TransCheckInfo
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = TransCheckInfo
                Namespace URI = http://model.webService.ebusiness.sinosoft.com/xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for Operator
                        */

                        
                                    protected java.lang.String localOperator ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOperatorTracker = false ;

                           public boolean isOperatorSpecified(){
                               return localOperatorTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOperator(){
                               return localOperator;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Operator
                               */
                               public void setOperator(java.lang.String param){
                            localOperatorTracker = true;
                                   
                                            this.localOperator=param;
                                    

                               }
                            

                        /**
                        * field for OperatorKey
                        */

                        
                                    protected java.lang.String localOperatorKey ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOperatorKeyTracker = false ;

                           public boolean isOperatorKeySpecified(){
                               return localOperatorKeyTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOperatorKey(){
                               return localOperatorKey;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OperatorKey
                               */
                               public void setOperatorKey(java.lang.String param){
                            localOperatorKeyTracker = true;
                                   
                                            this.localOperatorKey=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://model.webService.ebusiness.sinosoft.com/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":TransCheckInfo",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "TransCheckInfo",
                           xmlWriter);
                   }

               
                   }
                if (localOperatorTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "operator", xmlWriter);
                             

                                          if (localOperator==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOperator);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOperatorKeyTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "operatorKey", xmlWriter);
                             

                                          if (localOperatorKey==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOperatorKey);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://model.webService.ebusiness.sinosoft.com/xsd")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localOperatorTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "operator"));
                                 
                                         elementList.add(localOperator==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOperator));
                                    } if (localOperatorKeyTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "operatorKey"));
                                 
                                         elementList.add(localOperatorKey==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOperatorKey));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static TransCheckInfo parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            TransCheckInfo object =
                new TransCheckInfo();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"TransCheckInfo".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (TransCheckInfo)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","operator").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOperator(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","operatorKey").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOperatorKey(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class ResultDTO
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = ResultDTO
                Namespace URI = http://model.webService.ebusiness.sinosoft.com/xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for ResultCode
                        */

                        
                                    protected java.lang.String localResultCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResultCodeTracker = false ;

                           public boolean isResultCodeSpecified(){
                               return localResultCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResultCode(){
                               return localResultCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResultCode
                               */
                               public void setResultCode(java.lang.String param){
                            localResultCodeTracker = true;
                                   
                                            this.localResultCode=param;
                                    

                               }
                            

                        /**
                        * field for ResultInfoDesc
                        */

                        
                                    protected java.lang.String localResultInfoDesc ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResultInfoDescTracker = false ;

                           public boolean isResultInfoDescSpecified(){
                               return localResultInfoDescTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResultInfoDesc(){
                               return localResultInfoDesc;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResultInfoDesc
                               */
                               public void setResultInfoDesc(java.lang.String param){
                            localResultInfoDescTracker = true;
                                   
                                            this.localResultInfoDesc=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://model.webService.ebusiness.sinosoft.com/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":ResultDTO",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "ResultDTO",
                           xmlWriter);
                   }

               
                   }
                if (localResultCodeTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "resultCode", xmlWriter);
                             

                                          if (localResultCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResultCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResultInfoDescTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "resultInfoDesc", xmlWriter);
                             

                                          if (localResultInfoDesc==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResultInfoDesc);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://model.webService.ebusiness.sinosoft.com/xsd")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localResultCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "resultCode"));
                                 
                                         elementList.add(localResultCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResultCode));
                                    } if (localResultInfoDescTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "resultInfoDesc"));
                                 
                                         elementList.add(localResultInfoDesc==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResultInfoDesc));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static ResultDTO parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            ResultDTO object =
                new ResultDTO();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"ResultDTO".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (ResultDTO)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","resultCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResultCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","resultInfoDesc").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResultInfoDesc(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class FDInsCom
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = FDInsCom
                Namespace URI = http://pojo.webService.ebusiness.sinosoft.com/xsd
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for EMail
                        */

                        
                                    protected java.lang.String localEMail ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEMailTracker = false ;

                           public boolean isEMailSpecified(){
                               return localEMailTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEMail(){
                               return localEMail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EMail
                               */
                               public void setEMail(java.lang.String param){
                            localEMailTracker = true;
                                   
                                            this.localEMail=param;
                                    

                               }
                            

                        /**
                        * field for FMClaimDataTitle
                        * This was an Array!
                        */

                        
                                    protected FMClaimDataTitle[] localFMClaimDataTitle ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFMClaimDataTitleTracker = false ;

                           public boolean isFMClaimDataTitleSpecified(){
                               return localFMClaimDataTitleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return FMClaimDataTitle[]
                           */
                           public  FMClaimDataTitle[] getFMClaimDataTitle(){
                               return localFMClaimDataTitle;
                           }

                           
                        


                               
                              /**
                               * validate the array for FMClaimDataTitle
                               */
                              protected void validateFMClaimDataTitle(FMClaimDataTitle[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param FMClaimDataTitle
                              */
                              public void setFMClaimDataTitle(FMClaimDataTitle[] param){
                              
                                   validateFMClaimDataTitle(param);

                               localFMClaimDataTitleTracker = true;
                                      
                                      this.localFMClaimDataTitle=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param FMClaimDataTitle
                             */
                             public void addFMClaimDataTitle(FMClaimDataTitle param){
                                   if (localFMClaimDataTitle == null){
                                   localFMClaimDataTitle = new FMClaimDataTitle[]{};
                                   }

                            
                                 //update the setting tracker
                                localFMClaimDataTitleTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localFMClaimDataTitle);
                               list.add(param);
                               this.localFMClaimDataTitle =
                             (FMClaimDataTitle[])list.toArray(
                            new FMClaimDataTitle[list.size()]);

                             }
                             

                        /**
                        * field for FMClaimGuide
                        * This was an Array!
                        */

                        
                                    protected FMClaimGuide[] localFMClaimGuide ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFMClaimGuideTracker = false ;

                           public boolean isFMClaimGuideSpecified(){
                               return localFMClaimGuideTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return FMClaimGuide[]
                           */
                           public  FMClaimGuide[] getFMClaimGuide(){
                               return localFMClaimGuide;
                           }

                           
                        


                               
                              /**
                               * validate the array for FMClaimGuide
                               */
                              protected void validateFMClaimGuide(FMClaimGuide[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param FMClaimGuide
                              */
                              public void setFMClaimGuide(FMClaimGuide[] param){
                              
                                   validateFMClaimGuide(param);

                               localFMClaimGuideTracker = true;
                                      
                                      this.localFMClaimGuide=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param FMClaimGuide
                             */
                             public void addFMClaimGuide(FMClaimGuide param){
                                   if (localFMClaimGuide == null){
                                   localFMClaimGuide = new FMClaimGuide[]{};
                                   }

                            
                                 //update the setting tracker
                                localFMClaimGuideTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localFMClaimGuide);
                               list.add(param);
                               this.localFMClaimGuide =
                             (FMClaimGuide[])list.toArray(
                            new FMClaimGuide[list.size()]);

                             }
                             

                        /**
                        * field for AreaCode
                        */

                        
                                    protected java.lang.String localAreaCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAreaCodeTracker = false ;

                           public boolean isAreaCodeSpecified(){
                               return localAreaCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAreaCode(){
                               return localAreaCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AreaCode
                               */
                               public void setAreaCode(java.lang.String param){
                            localAreaCodeTracker = true;
                                   
                                            this.localAreaCode=param;
                                    

                               }
                            

                        /**
                        * field for Asset
                        */

                        
                                    protected double localAsset ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAssetTracker = false ;

                           public boolean isAssetSpecified(){
                               return localAssetTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getAsset(){
                               return localAsset;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Asset
                               */
                               public void setAsset(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localAssetTracker =
                                       !java.lang.Double.isNaN(param);
                                   
                                            this.localAsset=param;
                                    

                               }
                            

                        /**
                        * field for AutoRenewFlag
                        */

                        
                                    protected java.lang.String localAutoRenewFlag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAutoRenewFlagTracker = false ;

                           public boolean isAutoRenewFlagSpecified(){
                               return localAutoRenewFlagTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAutoRenewFlag(){
                               return localAutoRenewFlag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AutoRenewFlag
                               */
                               public void setAutoRenewFlag(java.lang.String param){
                            localAutoRenewFlagTracker = true;
                                   
                                            this.localAutoRenewFlag=param;
                                    

                               }
                            

                        /**
                        * field for BankAccNo
                        */

                        
                                    protected java.lang.String localBankAccNo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBankAccNoTracker = false ;

                           public boolean isBankAccNoSpecified(){
                               return localBankAccNoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBankAccNo(){
                               return localBankAccNo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BankAccNo
                               */
                               public void setBankAccNo(java.lang.String param){
                            localBankAccNoTracker = true;
                                   
                                            this.localBankAccNo=param;
                                    

                               }
                            

                        /**
                        * field for BankAddress
                        */

                        
                                    protected java.lang.String localBankAddress ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBankAddressTracker = false ;

                           public boolean isBankAddressSpecified(){
                               return localBankAddressTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBankAddress(){
                               return localBankAddress;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BankAddress
                               */
                               public void setBankAddress(java.lang.String param){
                            localBankAddressTracker = true;
                                   
                                            this.localBankAddress=param;
                                    

                               }
                            

                        /**
                        * field for BankCode
                        */

                        
                                    protected java.lang.String localBankCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBankCodeTracker = false ;

                           public boolean isBankCodeSpecified(){
                               return localBankCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBankCode(){
                               return localBankCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BankCode
                               */
                               public void setBankCode(java.lang.String param){
                            localBankCodeTracker = true;
                                   
                                            this.localBankCode=param;
                                    

                               }
                            

                        /**
                        * field for BlacklistFlag
                        */

                        
                                    protected java.lang.String localBlacklistFlag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBlacklistFlagTracker = false ;

                           public boolean isBlacklistFlagSpecified(){
                               return localBlacklistFlagTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBlacklistFlag(){
                               return localBlacklistFlag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BlacklistFlag
                               */
                               public void setBlacklistFlag(java.lang.String param){
                            localBlacklistFlagTracker = true;
                                   
                                            this.localBlacklistFlag=param;
                                    

                               }
                            

                        /**
                        * field for CityCode
                        */

                        
                                    protected java.lang.String localCityCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCityCodeTracker = false ;

                           public boolean isCityCodeSpecified(){
                               return localCityCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCityCode(){
                               return localCityCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CityCode
                               */
                               public void setCityCode(java.lang.String param){
                            localCityCodeTracker = true;
                                   
                                            this.localCityCode=param;
                                    

                               }
                            

                        /**
                        * field for ComAera
                        */

                        
                                    protected java.lang.String localComAera ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localComAeraTracker = false ;

                           public boolean isComAeraSpecified(){
                               return localComAeraTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getComAera(){
                               return localComAera;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ComAera
                               */
                               public void setComAera(java.lang.String param){
                            localComAeraTracker = true;
                                   
                                            this.localComAera=param;
                                    

                               }
                            

                        /**
                        * field for CompanyDesc
                        */

                        
                                    protected java.lang.String localCompanyDesc ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCompanyDescTracker = false ;

                           public boolean isCompanyDescSpecified(){
                               return localCompanyDescTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCompanyDesc(){
                               return localCompanyDesc;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CompanyDesc
                               */
                               public void setCompanyDesc(java.lang.String param){
                            localCompanyDescTracker = true;
                                   
                                            this.localCompanyDesc=param;
                                    

                               }
                            

                        /**
                        * field for Corporation
                        */

                        
                                    protected java.lang.String localCorporation ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCorporationTracker = false ;

                           public boolean isCorporationSpecified(){
                               return localCorporationTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCorporation(){
                               return localCorporation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Corporation
                               */
                               public void setCorporation(java.lang.String param){
                            localCorporationTracker = true;
                                   
                                            this.localCorporation=param;
                                    

                               }
                            

                        /**
                        * field for CountryCode
                        */

                        
                                    protected java.lang.String localCountryCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCountryCodeTracker = false ;

                           public boolean isCountryCodeSpecified(){
                               return localCountryCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCountryCode(){
                               return localCountryCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CountryCode
                               */
                               public void setCountryCode(java.lang.String param){
                            localCountryCodeTracker = true;
                                   
                                            this.localCountryCode=param;
                                    

                               }
                            

                        /**
                        * field for CustomerNo
                        */

                        
                                    protected java.lang.String localCustomerNo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerNoTracker = false ;

                           public boolean isCustomerNoSpecified(){
                               return localCustomerNoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerNo(){
                               return localCustomerNo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerNo
                               */
                               public void setCustomerNo(java.lang.String param){
                            localCustomerNoTracker = true;
                                   
                                            this.localCustomerNo=param;
                                    

                               }
                            

                        /**
                        * field for Department1
                        */

                        
                                    protected java.lang.String localDepartment1 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDepartment1Tracker = false ;

                           public boolean isDepartment1Specified(){
                               return localDepartment1Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDepartment1(){
                               return localDepartment1;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Department1
                               */
                               public void setDepartment1(java.lang.String param){
                            localDepartment1Tracker = true;
                                   
                                            this.localDepartment1=param;
                                    

                               }
                            

                        /**
                        * field for Department2
                        */

                        
                                    protected java.lang.String localDepartment2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDepartment2Tracker = false ;

                           public boolean isDepartment2Specified(){
                               return localDepartment2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDepartment2(){
                               return localDepartment2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Department2
                               */
                               public void setDepartment2(java.lang.String param){
                            localDepartment2Tracker = true;
                                   
                                            this.localDepartment2=param;
                                    

                               }
                            

                        /**
                        * field for E_Mail1
                        */

                        
                                    protected java.lang.String localE_Mail1 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localE_Mail1Tracker = false ;

                           public boolean isE_Mail1Specified(){
                               return localE_Mail1Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getE_Mail1(){
                               return localE_Mail1;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param E_Mail1
                               */
                               public void setE_Mail1(java.lang.String param){
                            localE_Mail1Tracker = true;
                                   
                                            this.localE_Mail1=param;
                                    

                               }
                            

                        /**
                        * field for E_Mail2
                        */

                        
                                    protected java.lang.String localE_Mail2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localE_Mail2Tracker = false ;

                           public boolean isE_Mail2Specified(){
                               return localE_Mail2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getE_Mail2(){
                               return localE_Mail2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param E_Mail2
                               */
                               public void setE_Mail2(java.lang.String param){
                            localE_Mail2Tracker = true;
                                   
                                            this.localE_Mail2=param;
                                    

                               }
                            

                        /**
                        * field for Fax
                        */

                        
                                    protected java.lang.String localFax ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFaxTracker = false ;

                           public boolean isFaxSpecified(){
                               return localFaxTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFax(){
                               return localFax;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Fax
                               */
                               public void setFax(java.lang.String param){
                            localFaxTracker = true;
                                   
                                            this.localFax=param;
                                    

                               }
                            

                        /**
                        * field for Fax1
                        */

                        
                                    protected java.lang.String localFax1 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFax1Tracker = false ;

                           public boolean isFax1Specified(){
                               return localFax1Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFax1(){
                               return localFax1;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Fax1
                               */
                               public void setFax1(java.lang.String param){
                            localFax1Tracker = true;
                                   
                                            this.localFax1=param;
                                    

                               }
                            

                        /**
                        * field for Fax2
                        */

                        
                                    protected java.lang.String localFax2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFax2Tracker = false ;

                           public boolean isFax2Specified(){
                               return localFax2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFax2(){
                               return localFax2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Fax2
                               */
                               public void setFax2(java.lang.String param){
                            localFax2Tracker = true;
                                   
                                            this.localFax2=param;
                                    

                               }
                            

                        /**
                        * field for FoundDate
                        */

                        
                                    protected java.lang.String localFoundDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFoundDateTracker = false ;

                           public boolean isFoundDateSpecified(){
                               return localFoundDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFoundDate(){
                               return localFoundDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FoundDate
                               */
                               public void setFoundDate(java.lang.String param){
                            localFoundDateTracker = true;
                                   
                                            this.localFoundDate=param;
                                    

                               }
                            

                        /**
                        * field for GetFlag
                        */

                        
                                    protected java.lang.String localGetFlag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGetFlagTracker = false ;

                           public boolean isGetFlagSpecified(){
                               return localGetFlagTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getGetFlag(){
                               return localGetFlag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GetFlag
                               */
                               public void setGetFlag(java.lang.String param){
                            localGetFlagTracker = true;
                                   
                                            this.localGetFlag=param;
                                    

                               }
                            

                        /**
                        * field for HeadShip1
                        */

                        
                                    protected java.lang.String localHeadShip1 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHeadShip1Tracker = false ;

                           public boolean isHeadShip1Specified(){
                               return localHeadShip1Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHeadShip1(){
                               return localHeadShip1;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HeadShip1
                               */
                               public void setHeadShip1(java.lang.String param){
                            localHeadShip1Tracker = true;
                                   
                                            this.localHeadShip1=param;
                                    

                               }
                            

                        /**
                        * field for HeadShip2
                        */

                        
                                    protected java.lang.String localHeadShip2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHeadShip2Tracker = false ;

                           public boolean isHeadShip2Specified(){
                               return localHeadShip2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHeadShip2(){
                               return localHeadShip2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HeadShip2
                               */
                               public void setHeadShip2(java.lang.String param){
                            localHeadShip2Tracker = true;
                                   
                                            this.localHeadShip2=param;
                                    

                               }
                            

                        /**
                        * field for Hotline
                        */

                        
                                    protected java.lang.String localHotline ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHotlineTracker = false ;

                           public boolean isHotlineSpecified(){
                               return localHotlineTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHotline(){
                               return localHotline;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Hotline
                               */
                               public void setHotline(java.lang.String param){
                            localHotlineTracker = true;
                                   
                                            this.localHotline=param;
                                    

                               }
                            

                        /**
                        * field for InsAddress
                        */

                        
                                    protected java.lang.String localInsAddress ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInsAddressTracker = false ;

                           public boolean isInsAddressSpecified(){
                               return localInsAddressTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInsAddress(){
                               return localInsAddress;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InsAddress
                               */
                               public void setInsAddress(java.lang.String param){
                            localInsAddressTracker = true;
                                   
                                            this.localInsAddress=param;
                                    

                               }
                            

                        /**
                        * field for InsAddressCode
                        */

                        
                                    protected java.lang.String localInsAddressCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInsAddressCodeTracker = false ;

                           public boolean isInsAddressCodeSpecified(){
                               return localInsAddressCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInsAddressCode(){
                               return localInsAddressCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InsAddressCode
                               */
                               public void setInsAddressCode(java.lang.String param){
                            localInsAddressCodeTracker = true;
                                   
                                            this.localInsAddressCode=param;
                                    

                               }
                            

                        /**
                        * field for InsCategory
                        */

                        
                                    protected java.lang.String localInsCategory ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInsCategoryTracker = false ;

                           public boolean isInsCategorySpecified(){
                               return localInsCategoryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInsCategory(){
                               return localInsCategory;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InsCategory
                               */
                               public void setInsCategory(java.lang.String param){
                            localInsCategoryTracker = true;
                                   
                                            this.localInsCategory=param;
                                    

                               }
                            

                        /**
                        * field for InsClass
                        */

                        
                                    protected java.lang.String localInsClass ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInsClassTracker = false ;

                           public boolean isInsClassSpecified(){
                               return localInsClassTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInsClass(){
                               return localInsClass;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InsClass
                               */
                               public void setInsClass(java.lang.String param){
                            localInsClassTracker = true;
                                   
                                            this.localInsClass=param;
                                    

                               }
                            

                        /**
                        * field for InsSuperCode
                        */

                        
                                    protected java.lang.String localInsSuperCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInsSuperCodeTracker = false ;

                           public boolean isInsSuperCodeSpecified(){
                               return localInsSuperCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInsSuperCode(){
                               return localInsSuperCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InsSuperCode
                               */
                               public void setInsSuperCode(java.lang.String param){
                            localInsSuperCodeTracker = true;
                                   
                                            this.localInsSuperCode=param;
                                    

                               }
                            

                        /**
                        * field for InsType
                        */

                        
                                    protected java.lang.String localInsType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInsTypeTracker = false ;

                           public boolean isInsTypeSpecified(){
                               return localInsTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInsType(){
                               return localInsType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InsType
                               */
                               public void setInsType(java.lang.String param){
                            localInsTypeTracker = true;
                                   
                                            this.localInsType=param;
                                    

                               }
                            

                        /**
                        * field for InsZipCode
                        */

                        
                                    protected java.lang.String localInsZipCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInsZipCodeTracker = false ;

                           public boolean isInsZipCodeSpecified(){
                               return localInsZipCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInsZipCode(){
                               return localInsZipCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InsZipCode
                               */
                               public void setInsZipCode(java.lang.String param){
                            localInsZipCodeTracker = true;
                                   
                                            this.localInsZipCode=param;
                                    

                               }
                            

                        /**
                        * field for LinkCustomerNo1
                        */

                        
                                    protected java.lang.String localLinkCustomerNo1 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLinkCustomerNo1Tracker = false ;

                           public boolean isLinkCustomerNo1Specified(){
                               return localLinkCustomerNo1Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLinkCustomerNo1(){
                               return localLinkCustomerNo1;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LinkCustomerNo1
                               */
                               public void setLinkCustomerNo1(java.lang.String param){
                            localLinkCustomerNo1Tracker = true;
                                   
                                            this.localLinkCustomerNo1=param;
                                    

                               }
                            

                        /**
                        * field for LinkCustomerNo2
                        */

                        
                                    protected java.lang.String localLinkCustomerNo2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLinkCustomerNo2Tracker = false ;

                           public boolean isLinkCustomerNo2Specified(){
                               return localLinkCustomerNo2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLinkCustomerNo2(){
                               return localLinkCustomerNo2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LinkCustomerNo2
                               */
                               public void setLinkCustomerNo2(java.lang.String param){
                            localLinkCustomerNo2Tracker = true;
                                   
                                            this.localLinkCustomerNo2=param;
                                    

                               }
                            

                        /**
                        * field for LinkCustomerNo3
                        */

                        
                                    protected java.lang.String localLinkCustomerNo3 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLinkCustomerNo3Tracker = false ;

                           public boolean isLinkCustomerNo3Specified(){
                               return localLinkCustomerNo3Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLinkCustomerNo3(){
                               return localLinkCustomerNo3;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LinkCustomerNo3
                               */
                               public void setLinkCustomerNo3(java.lang.String param){
                            localLinkCustomerNo3Tracker = true;
                                   
                                            this.localLinkCustomerNo3=param;
                                    

                               }
                            

                        /**
                        * field for LinkCustomerNo4
                        */

                        
                                    protected java.lang.String localLinkCustomerNo4 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLinkCustomerNo4Tracker = false ;

                           public boolean isLinkCustomerNo4Specified(){
                               return localLinkCustomerNo4Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLinkCustomerNo4(){
                               return localLinkCustomerNo4;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LinkCustomerNo4
                               */
                               public void setLinkCustomerNo4(java.lang.String param){
                            localLinkCustomerNo4Tracker = true;
                                   
                                            this.localLinkCustomerNo4=param;
                                    

                               }
                            

                        /**
                        * field for LinkMan1
                        */

                        
                                    protected java.lang.String localLinkMan1 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLinkMan1Tracker = false ;

                           public boolean isLinkMan1Specified(){
                               return localLinkMan1Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLinkMan1(){
                               return localLinkMan1;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LinkMan1
                               */
                               public void setLinkMan1(java.lang.String param){
                            localLinkMan1Tracker = true;
                                   
                                            this.localLinkMan1=param;
                                    

                               }
                            

                        /**
                        * field for LinkMan2
                        */

                        
                                    protected java.lang.String localLinkMan2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLinkMan2Tracker = false ;

                           public boolean isLinkMan2Specified(){
                               return localLinkMan2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLinkMan2(){
                               return localLinkMan2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LinkMan2
                               */
                               public void setLinkMan2(java.lang.String param){
                            localLinkMan2Tracker = true;
                                   
                                            this.localLinkMan2=param;
                                    

                               }
                            

                        /**
                        * field for LinkMan3
                        */

                        
                                    protected java.lang.String localLinkMan3 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLinkMan3Tracker = false ;

                           public boolean isLinkMan3Specified(){
                               return localLinkMan3Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLinkMan3(){
                               return localLinkMan3;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LinkMan3
                               */
                               public void setLinkMan3(java.lang.String param){
                            localLinkMan3Tracker = true;
                                   
                                            this.localLinkMan3=param;
                                    

                               }
                            

                        /**
                        * field for LinkMan4
                        */

                        
                                    protected java.lang.String localLinkMan4 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLinkMan4Tracker = false ;

                           public boolean isLinkMan4Specified(){
                               return localLinkMan4Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLinkMan4(){
                               return localLinkMan4;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LinkMan4
                               */
                               public void setLinkMan4(java.lang.String param){
                            localLinkMan4Tracker = true;
                                   
                                            this.localLinkMan4=param;
                                    

                               }
                            

                        /**
                        * field for LogoRealPath
                        */

                        
                                    protected java.lang.String localLogoRealPath ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLogoRealPathTracker = false ;

                           public boolean isLogoRealPathSpecified(){
                               return localLogoRealPathTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLogoRealPath(){
                               return localLogoRealPath;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LogoRealPath
                               */
                               public void setLogoRealPath(java.lang.String param){
                            localLogoRealPathTracker = true;
                                   
                                            this.localLogoRealPath=param;
                                    

                               }
                            

                        /**
                        * field for LogoRelaPath
                        */

                        
                                    protected java.lang.String localLogoRelaPath ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLogoRelaPathTracker = false ;

                           public boolean isLogoRelaPathSpecified(){
                               return localLogoRelaPathTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLogoRelaPath(){
                               return localLogoRelaPath;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LogoRelaPath
                               */
                               public void setLogoRelaPath(java.lang.String param){
                            localLogoRelaPathTracker = true;
                                   
                                            this.localLogoRelaPath=param;
                                    

                               }
                            

                        /**
                        * field for MainBussiness
                        */

                        
                                    protected java.lang.String localMainBussiness ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMainBussinessTracker = false ;

                           public boolean isMainBussinessSpecified(){
                               return localMainBussinessTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMainBussiness(){
                               return localMainBussiness;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MainBussiness
                               */
                               public void setMainBussiness(java.lang.String param){
                            localMainBussinessTracker = true;
                                   
                                            this.localMainBussiness=param;
                                    

                               }
                            

                        /**
                        * field for MakeDate
                        */

                        
                                    protected java.lang.String localMakeDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMakeDateTracker = false ;

                           public boolean isMakeDateSpecified(){
                               return localMakeDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMakeDate(){
                               return localMakeDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MakeDate
                               */
                               public void setMakeDate(java.lang.String param){
                            localMakeDateTracker = true;
                                   
                                            this.localMakeDate=param;
                                    

                               }
                            

                        /**
                        * field for MakeTime
                        */

                        
                                    protected java.lang.String localMakeTime ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMakeTimeTracker = false ;

                           public boolean isMakeTimeSpecified(){
                               return localMakeTimeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMakeTime(){
                               return localMakeTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MakeTime
                               */
                               public void setMakeTime(java.lang.String param){
                            localMakeTimeTracker = true;
                                   
                                            this.localMakeTime=param;
                                    

                               }
                            

                        /**
                        * field for MasterInscom
                        */

                        
                                    protected java.lang.String localMasterInscom ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMasterInscomTracker = false ;

                           public boolean isMasterInscomSpecified(){
                               return localMasterInscomTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMasterInscom(){
                               return localMasterInscom;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MasterInscom
                               */
                               public void setMasterInscom(java.lang.String param){
                            localMasterInscomTracker = true;
                                   
                                            this.localMasterInscom=param;
                                    

                               }
                            

                        /**
                        * field for Mobile1
                        */

                        
                                    protected java.lang.String localMobile1 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMobile1Tracker = false ;

                           public boolean isMobile1Specified(){
                               return localMobile1Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMobile1(){
                               return localMobile1;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Mobile1
                               */
                               public void setMobile1(java.lang.String param){
                            localMobile1Tracker = true;
                                   
                                            this.localMobile1=param;
                                    

                               }
                            

                        /**
                        * field for Mobile2
                        */

                        
                                    protected java.lang.String localMobile2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMobile2Tracker = false ;

                           public boolean isMobile2Specified(){
                               return localMobile2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMobile2(){
                               return localMobile2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Mobile2
                               */
                               public void setMobile2(java.lang.String param){
                            localMobile2Tracker = true;
                                   
                                            this.localMobile2=param;
                                    

                               }
                            

                        /**
                        * field for Mobile3
                        */

                        
                                    protected java.lang.String localMobile3 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMobile3Tracker = false ;

                           public boolean isMobile3Specified(){
                               return localMobile3Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMobile3(){
                               return localMobile3;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Mobile3
                               */
                               public void setMobile3(java.lang.String param){
                            localMobile3Tracker = true;
                                   
                                            this.localMobile3=param;
                                    

                               }
                            

                        /**
                        * field for Mobile4
                        */

                        
                                    protected java.lang.String localMobile4 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMobile4Tracker = false ;

                           public boolean isMobile4Specified(){
                               return localMobile4Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMobile4(){
                               return localMobile4;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Mobile4
                               */
                               public void setMobile4(java.lang.String param){
                            localMobile4Tracker = true;
                                   
                                            this.localMobile4=param;
                                    

                               }
                            

                        /**
                        * field for ModifyDate
                        */

                        
                                    protected java.lang.String localModifyDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localModifyDateTracker = false ;

                           public boolean isModifyDateSpecified(){
                               return localModifyDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getModifyDate(){
                               return localModifyDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ModifyDate
                               */
                               public void setModifyDate(java.lang.String param){
                            localModifyDateTracker = true;
                                   
                                            this.localModifyDate=param;
                                    

                               }
                            

                        /**
                        * field for ModifyTime
                        */

                        
                                    protected java.lang.String localModifyTime ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localModifyTimeTracker = false ;

                           public boolean isModifyTimeSpecified(){
                               return localModifyTimeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getModifyTime(){
                               return localModifyTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ModifyTime
                               */
                               public void setModifyTime(java.lang.String param){
                            localModifyTimeTracker = true;
                                   
                                            this.localModifyTime=param;
                                    

                               }
                            

                        /**
                        * field for NetProfitRate
                        */

                        
                                    protected double localNetProfitRate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNetProfitRateTracker = false ;

                           public boolean isNetProfitRateSpecified(){
                               return localNetProfitRateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getNetProfitRate(){
                               return localNetProfitRate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NetProfitRate
                               */
                               public void setNetProfitRate(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localNetProfitRateTracker =
                                       !java.lang.Double.isNaN(param);
                                   
                                            this.localNetProfitRate=param;
                                    

                               }
                            

                        /**
                        * field for Operator
                        */

                        
                                    protected java.lang.String localOperator ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOperatorTracker = false ;

                           public boolean isOperatorSpecified(){
                               return localOperatorTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOperator(){
                               return localOperator;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Operator
                               */
                               public void setOperator(java.lang.String param){
                            localOperatorTracker = true;
                                   
                                            this.localOperator=param;
                                    

                               }
                            

                        /**
                        * field for Password
                        */

                        
                                    protected java.lang.String localPassword ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPasswordTracker = false ;

                           public boolean isPasswordSpecified(){
                               return localPasswordTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPassword(){
                               return localPassword;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Password
                               */
                               public void setPassword(java.lang.String param){
                            localPasswordTracker = true;
                                   
                                            this.localPassword=param;
                                    

                               }
                            

                        /**
                        * field for Peoples
                        */

                        
                                    protected int localPeoples ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPeoplesTracker = false ;

                           public boolean isPeoplesSpecified(){
                               return localPeoplesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getPeoples(){
                               return localPeoples;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Peoples
                               */
                               public void setPeoples(int param){
                            
                                       // setting primitive attribute tracker to true
                                       localPeoplesTracker =
                                       param != java.lang.Integer.MIN_VALUE;
                                   
                                            this.localPeoples=param;
                                    

                               }
                            

                        /**
                        * field for Phone
                        */

                        
                                    protected java.lang.String localPhone ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPhoneTracker = false ;

                           public boolean isPhoneSpecified(){
                               return localPhoneTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPhone(){
                               return localPhone;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Phone
                               */
                               public void setPhone(java.lang.String param){
                            localPhoneTracker = true;
                                   
                                            this.localPhone=param;
                                    

                               }
                            

                        /**
                        * field for Phone1
                        */

                        
                                    protected java.lang.String localPhone1 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPhone1Tracker = false ;

                           public boolean isPhone1Specified(){
                               return localPhone1Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPhone1(){
                               return localPhone1;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Phone1
                               */
                               public void setPhone1(java.lang.String param){
                            localPhone1Tracker = true;
                                   
                                            this.localPhone1=param;
                                    

                               }
                            

                        /**
                        * field for Phone2
                        */

                        
                                    protected java.lang.String localPhone2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPhone2Tracker = false ;

                           public boolean isPhone2Specified(){
                               return localPhone2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPhone2(){
                               return localPhone2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Phone2
                               */
                               public void setPhone2(java.lang.String param){
                            localPhone2Tracker = true;
                                   
                                            this.localPhone2=param;
                                    

                               }
                            

                        /**
                        * field for ProvinceCode
                        */

                        
                                    protected java.lang.String localProvinceCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProvinceCodeTracker = false ;

                           public boolean isProvinceCodeSpecified(){
                               return localProvinceCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProvinceCode(){
                               return localProvinceCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ProvinceCode
                               */
                               public void setProvinceCode(java.lang.String param){
                            localProvinceCodeTracker = true;
                                   
                                            this.localProvinceCode=param;
                                    

                               }
                            

                        /**
                        * field for Remark
                        */

                        
                                    protected java.lang.String localRemark ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRemarkTracker = false ;

                           public boolean isRemarkSpecified(){
                               return localRemarkTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRemark(){
                               return localRemark;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Remark
                               */
                               public void setRemark(java.lang.String param){
                            localRemarkTracker = true;
                                   
                                            this.localRemark=param;
                                    

                               }
                            

                        /**
                        * field for RgtMoney
                        */

                        
                                    protected double localRgtMoney ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRgtMoneyTracker = false ;

                           public boolean isRgtMoneySpecified(){
                               return localRgtMoneyTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getRgtMoney(){
                               return localRgtMoney;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RgtMoney
                               */
                               public void setRgtMoney(double param){
                            
                                       // setting primitive attribute tracker to true
                                       localRgtMoneyTracker =
                                       !java.lang.Double.isNaN(param);
                                   
                                            this.localRgtMoney=param;
                                    

                               }
                            

                        /**
                        * field for Satrap
                        */

                        
                                    protected java.lang.String localSatrap ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSatrapTracker = false ;

                           public boolean isSatrapSpecified(){
                               return localSatrapTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSatrap(){
                               return localSatrap;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Satrap
                               */
                               public void setSatrap(java.lang.String param){
                            localSatrapTracker = true;
                                   
                                            this.localSatrap=param;
                                    

                               }
                            

                        /**
                        * field for ShortName
                        */

                        
                                    protected java.lang.String localShortName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localShortNameTracker = false ;

                           public boolean isShortNameSpecified(){
                               return localShortNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getShortName(){
                               return localShortName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ShortName
                               */
                               public void setShortName(java.lang.String param){
                            localShortNameTracker = true;
                                   
                                            this.localShortName=param;
                                    

                               }
                            

                        /**
                        * field for Status
                        */

                        
                                    protected java.lang.String localStatus ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStatusTracker = false ;

                           public boolean isStatusSpecified(){
                               return localStatusTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getStatus(){
                               return localStatus;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Status
                               */
                               public void setStatus(java.lang.String param){
                            localStatusTracker = true;
                                   
                                            this.localStatus=param;
                                    

                               }
                            

                        /**
                        * field for StopFlg
                        */

                        
                                    protected java.lang.String localStopFlg ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStopFlgTracker = false ;

                           public boolean isStopFlgSpecified(){
                               return localStopFlgTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getStopFlg(){
                               return localStopFlg;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param StopFlg
                               */
                               public void setStopFlg(java.lang.String param){
                            localStopFlgTracker = true;
                                   
                                            this.localStopFlg=param;
                                    

                               }
                            

                        /**
                        * field for SupplierCode
                        */

                        
                                    protected java.lang.String localSupplierCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSupplierCodeTracker = false ;

                           public boolean isSupplierCodeSpecified(){
                               return localSupplierCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSupplierCode(){
                               return localSupplierCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SupplierCode
                               */
                               public void setSupplierCode(java.lang.String param){
                            localSupplierCodeTracker = true;
                                   
                                            this.localSupplierCode=param;
                                    

                               }
                            

                        /**
                        * field for SupplierDesc
                        */

                        
                                    protected java.lang.String localSupplierDesc ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSupplierDescTracker = false ;

                           public boolean isSupplierDescSpecified(){
                               return localSupplierDescTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSupplierDesc(){
                               return localSupplierDesc;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SupplierDesc
                               */
                               public void setSupplierDesc(java.lang.String param){
                            localSupplierDescTracker = true;
                                   
                                            this.localSupplierDesc=param;
                                    

                               }
                            

                        /**
                        * field for SupplierEngName
                        */

                        
                                    protected java.lang.String localSupplierEngName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSupplierEngNameTracker = false ;

                           public boolean isSupplierEngNameSpecified(){
                               return localSupplierEngNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSupplierEngName(){
                               return localSupplierEngName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SupplierEngName
                               */
                               public void setSupplierEngName(java.lang.String param){
                            localSupplierEngNameTracker = true;
                                   
                                            this.localSupplierEngName=param;
                                    

                               }
                            

                        /**
                        * field for SupplierName
                        */

                        
                                    protected java.lang.String localSupplierName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSupplierNameTracker = false ;

                           public boolean isSupplierNameSpecified(){
                               return localSupplierNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSupplierName(){
                               return localSupplierName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SupplierName
                               */
                               public void setSupplierName(java.lang.String param){
                            localSupplierNameTracker = true;
                                   
                                            this.localSupplierName=param;
                                    

                               }
                            

                        /**
                        * field for SupplierShowCode
                        */

                        
                                    protected java.lang.String localSupplierShowCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSupplierShowCodeTracker = false ;

                           public boolean isSupplierShowCodeSpecified(){
                               return localSupplierShowCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSupplierShowCode(){
                               return localSupplierShowCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SupplierShowCode
                               */
                               public void setSupplierShowCode(java.lang.String param){
                            localSupplierShowCodeTracker = true;
                                   
                                            this.localSupplierShowCode=param;
                                    

                               }
                            

                        /**
                        * field for SynchroFlag
                        */

                        
                                    protected java.lang.String localSynchroFlag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSynchroFlagTracker = false ;

                           public boolean isSynchroFlagSpecified(){
                               return localSynchroFlagTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSynchroFlag(){
                               return localSynchroFlag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SynchroFlag
                               */
                               public void setSynchroFlag(java.lang.String param){
                            localSynchroFlagTracker = true;
                                   
                                            this.localSynchroFlag=param;
                                    

                               }
                            

                        /**
                        * field for WebSite
                        */

                        
                                    protected java.lang.String localWebSite ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWebSiteTracker = false ;

                           public boolean isWebSiteSpecified(){
                               return localWebSiteTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWebSite(){
                               return localWebSite;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WebSite
                               */
                               public void setWebSite(java.lang.String param){
                            localWebSiteTracker = true;
                                   
                                            this.localWebSite=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://pojo.webService.ebusiness.sinosoft.com/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":FDInsCom",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "FDInsCom",
                           xmlWriter);
                   }

               
                   }
                if (localEMailTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "EMail", xmlWriter);
                             

                                          if (localEMail==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEMail);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFMClaimDataTitleTracker){
                                       if (localFMClaimDataTitle!=null){
                                            for (int i = 0;i < localFMClaimDataTitle.length;i++){
                                                if (localFMClaimDataTitle[i] != null){
                                                 localFMClaimDataTitle[i].serialize(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","FMClaimDataTitle"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                            writeStartElement(null, "http://pojo.webService.ebusiness.sinosoft.com/xsd", "FMClaimDataTitle", xmlWriter);

                                                           // write the nil attribute
                                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                           xmlWriter.writeEndElement();
                                                    
                                                }

                                            }
                                     } else {
                                        
                                                writeStartElement(null, "http://pojo.webService.ebusiness.sinosoft.com/xsd", "FMClaimDataTitle", xmlWriter);

                                               // write the nil attribute
                                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                               xmlWriter.writeEndElement();
                                        
                                    }
                                 } if (localFMClaimGuideTracker){
                                       if (localFMClaimGuide!=null){
                                            for (int i = 0;i < localFMClaimGuide.length;i++){
                                                if (localFMClaimGuide[i] != null){
                                                 localFMClaimGuide[i].serialize(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","FMClaimGuide"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                            writeStartElement(null, "http://pojo.webService.ebusiness.sinosoft.com/xsd", "FMClaimGuide", xmlWriter);

                                                           // write the nil attribute
                                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                           xmlWriter.writeEndElement();
                                                    
                                                }

                                            }
                                     } else {
                                        
                                                writeStartElement(null, "http://pojo.webService.ebusiness.sinosoft.com/xsd", "FMClaimGuide", xmlWriter);

                                               // write the nil attribute
                                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                               xmlWriter.writeEndElement();
                                        
                                    }
                                 } if (localAreaCodeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "areaCode", xmlWriter);
                             

                                          if (localAreaCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAreaCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAssetTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "asset", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localAsset)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("asset cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAsset));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAutoRenewFlagTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "autoRenewFlag", xmlWriter);
                             

                                          if (localAutoRenewFlag==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAutoRenewFlag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBankAccNoTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "bankAccNo", xmlWriter);
                             

                                          if (localBankAccNo==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBankAccNo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBankAddressTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "bankAddress", xmlWriter);
                             

                                          if (localBankAddress==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBankAddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBankCodeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "bankCode", xmlWriter);
                             

                                          if (localBankCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBankCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBlacklistFlagTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "blacklistFlag", xmlWriter);
                             

                                          if (localBlacklistFlag==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBlacklistFlag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCityCodeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "cityCode", xmlWriter);
                             

                                          if (localCityCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCityCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localComAeraTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "comAera", xmlWriter);
                             

                                          if (localComAera==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localComAera);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCompanyDescTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "companyDesc", xmlWriter);
                             

                                          if (localCompanyDesc==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCompanyDesc);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCorporationTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "corporation", xmlWriter);
                             

                                          if (localCorporation==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCorporation);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCountryCodeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "countryCode", xmlWriter);
                             

                                          if (localCountryCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCountryCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerNoTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "customerNo", xmlWriter);
                             

                                          if (localCustomerNo==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerNo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDepartment1Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "department1", xmlWriter);
                             

                                          if (localDepartment1==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDepartment1);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDepartment2Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "department2", xmlWriter);
                             

                                          if (localDepartment2==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDepartment2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localE_Mail1Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "e_Mail1", xmlWriter);
                             

                                          if (localE_Mail1==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localE_Mail1);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localE_Mail2Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "e_Mail2", xmlWriter);
                             

                                          if (localE_Mail2==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localE_Mail2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFaxTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "fax", xmlWriter);
                             

                                          if (localFax==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFax);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFax1Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "fax1", xmlWriter);
                             

                                          if (localFax1==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFax1);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFax2Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "fax2", xmlWriter);
                             

                                          if (localFax2==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFax2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFoundDateTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "foundDate", xmlWriter);
                             

                                          if (localFoundDate==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFoundDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localGetFlagTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "getFlag", xmlWriter);
                             

                                          if (localGetFlag==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localGetFlag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHeadShip1Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "headShip1", xmlWriter);
                             

                                          if (localHeadShip1==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHeadShip1);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHeadShip2Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "headShip2", xmlWriter);
                             

                                          if (localHeadShip2==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHeadShip2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHotlineTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "hotline", xmlWriter);
                             

                                          if (localHotline==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHotline);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localInsAddressTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "insAddress", xmlWriter);
                             

                                          if (localInsAddress==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInsAddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localInsAddressCodeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "insAddressCode", xmlWriter);
                             

                                          if (localInsAddressCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInsAddressCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localInsCategoryTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "insCategory", xmlWriter);
                             

                                          if (localInsCategory==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInsCategory);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localInsClassTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "insClass", xmlWriter);
                             

                                          if (localInsClass==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInsClass);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localInsSuperCodeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "insSuperCode", xmlWriter);
                             

                                          if (localInsSuperCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInsSuperCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localInsTypeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "insType", xmlWriter);
                             

                                          if (localInsType==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInsType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localInsZipCodeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "insZipCode", xmlWriter);
                             

                                          if (localInsZipCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInsZipCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLinkCustomerNo1Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "linkCustomerNo1", xmlWriter);
                             

                                          if (localLinkCustomerNo1==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLinkCustomerNo1);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLinkCustomerNo2Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "linkCustomerNo2", xmlWriter);
                             

                                          if (localLinkCustomerNo2==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLinkCustomerNo2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLinkCustomerNo3Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "linkCustomerNo3", xmlWriter);
                             

                                          if (localLinkCustomerNo3==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLinkCustomerNo3);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLinkCustomerNo4Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "linkCustomerNo4", xmlWriter);
                             

                                          if (localLinkCustomerNo4==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLinkCustomerNo4);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLinkMan1Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "linkMan1", xmlWriter);
                             

                                          if (localLinkMan1==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLinkMan1);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLinkMan2Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "linkMan2", xmlWriter);
                             

                                          if (localLinkMan2==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLinkMan2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLinkMan3Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "linkMan3", xmlWriter);
                             

                                          if (localLinkMan3==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLinkMan3);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLinkMan4Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "linkMan4", xmlWriter);
                             

                                          if (localLinkMan4==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLinkMan4);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLogoRealPathTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "logoRealPath", xmlWriter);
                             

                                          if (localLogoRealPath==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLogoRealPath);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLogoRelaPathTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "logoRelaPath", xmlWriter);
                             

                                          if (localLogoRelaPath==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLogoRelaPath);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMainBussinessTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "mainBussiness", xmlWriter);
                             

                                          if (localMainBussiness==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMainBussiness);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMakeDateTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "makeDate", xmlWriter);
                             

                                          if (localMakeDate==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMakeDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMakeTimeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "makeTime", xmlWriter);
                             

                                          if (localMakeTime==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMakeTime);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMasterInscomTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "masterInscom", xmlWriter);
                             

                                          if (localMasterInscom==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMasterInscom);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMobile1Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "mobile1", xmlWriter);
                             

                                          if (localMobile1==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMobile1);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMobile2Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "mobile2", xmlWriter);
                             

                                          if (localMobile2==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMobile2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMobile3Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "mobile3", xmlWriter);
                             

                                          if (localMobile3==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMobile3);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMobile4Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "mobile4", xmlWriter);
                             

                                          if (localMobile4==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMobile4);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localModifyDateTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "modifyDate", xmlWriter);
                             

                                          if (localModifyDate==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localModifyDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localModifyTimeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "modifyTime", xmlWriter);
                             

                                          if (localModifyTime==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localModifyTime);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNetProfitRateTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "netProfitRate", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localNetProfitRate)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("netProfitRate cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNetProfitRate));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOperatorTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "operator", xmlWriter);
                             

                                          if (localOperator==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOperator);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPasswordTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "password", xmlWriter);
                             

                                          if (localPassword==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPassword);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPeoplesTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "peoples", xmlWriter);
                             
                                               if (localPeoples==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("peoples cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPeoples));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPhoneTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "phone", xmlWriter);
                             

                                          if (localPhone==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPhone);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPhone1Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "phone1", xmlWriter);
                             

                                          if (localPhone1==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPhone1);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPhone2Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "phone2", xmlWriter);
                             

                                          if (localPhone2==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPhone2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localProvinceCodeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "provinceCode", xmlWriter);
                             

                                          if (localProvinceCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProvinceCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRemarkTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "remark", xmlWriter);
                             

                                          if (localRemark==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRemark);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRgtMoneyTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "rgtMoney", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localRgtMoney)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("rgtMoney cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRgtMoney));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSatrapTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "satrap", xmlWriter);
                             

                                          if (localSatrap==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSatrap);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localShortNameTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "shortName", xmlWriter);
                             

                                          if (localShortName==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localShortName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localStatusTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "status", xmlWriter);
                             

                                          if (localStatus==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localStatus);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localStopFlgTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "stopFlg", xmlWriter);
                             

                                          if (localStopFlg==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localStopFlg);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSupplierCodeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "supplierCode", xmlWriter);
                             

                                          if (localSupplierCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSupplierCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSupplierDescTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "supplierDesc", xmlWriter);
                             

                                          if (localSupplierDesc==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSupplierDesc);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSupplierEngNameTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "supplierEngName", xmlWriter);
                             

                                          if (localSupplierEngName==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSupplierEngName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSupplierNameTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "supplierName", xmlWriter);
                             

                                          if (localSupplierName==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSupplierName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSupplierShowCodeTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "supplierShowCode", xmlWriter);
                             

                                          if (localSupplierShowCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSupplierShowCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSynchroFlagTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "synchroFlag", xmlWriter);
                             

                                          if (localSynchroFlag==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSynchroFlag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWebSiteTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "webSite", xmlWriter);
                             

                                          if (localWebSite==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWebSite);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://pojo.webService.ebusiness.sinosoft.com/xsd")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localEMailTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "EMail"));
                                 
                                         elementList.add(localEMail==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEMail));
                                    } if (localFMClaimDataTitleTracker){
                             if (localFMClaimDataTitle!=null) {
                                 for (int i = 0;i < localFMClaimDataTitle.length;i++){

                                    if (localFMClaimDataTitle[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                          "FMClaimDataTitle"));
                                         elementList.add(localFMClaimDataTitle[i]);
                                    } else {
                                        
                                                elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                          "FMClaimDataTitle"));
                                                elementList.add(null);
                                            
                                    }

                                 }
                             } else {
                                 
                                        elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                          "FMClaimDataTitle"));
                                        elementList.add(localFMClaimDataTitle);
                                    
                             }

                        } if (localFMClaimGuideTracker){
                             if (localFMClaimGuide!=null) {
                                 for (int i = 0;i < localFMClaimGuide.length;i++){

                                    if (localFMClaimGuide[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                          "FMClaimGuide"));
                                         elementList.add(localFMClaimGuide[i]);
                                    } else {
                                        
                                                elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                          "FMClaimGuide"));
                                                elementList.add(null);
                                            
                                    }

                                 }
                             } else {
                                 
                                        elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                          "FMClaimGuide"));
                                        elementList.add(localFMClaimGuide);
                                    
                             }

                        } if (localAreaCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "areaCode"));
                                 
                                         elementList.add(localAreaCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAreaCode));
                                    } if (localAssetTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "asset"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAsset));
                            } if (localAutoRenewFlagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "autoRenewFlag"));
                                 
                                         elementList.add(localAutoRenewFlag==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAutoRenewFlag));
                                    } if (localBankAccNoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "bankAccNo"));
                                 
                                         elementList.add(localBankAccNo==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBankAccNo));
                                    } if (localBankAddressTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "bankAddress"));
                                 
                                         elementList.add(localBankAddress==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBankAddress));
                                    } if (localBankCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "bankCode"));
                                 
                                         elementList.add(localBankCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBankCode));
                                    } if (localBlacklistFlagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "blacklistFlag"));
                                 
                                         elementList.add(localBlacklistFlag==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBlacklistFlag));
                                    } if (localCityCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "cityCode"));
                                 
                                         elementList.add(localCityCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCityCode));
                                    } if (localComAeraTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "comAera"));
                                 
                                         elementList.add(localComAera==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localComAera));
                                    } if (localCompanyDescTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "companyDesc"));
                                 
                                         elementList.add(localCompanyDesc==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCompanyDesc));
                                    } if (localCorporationTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "corporation"));
                                 
                                         elementList.add(localCorporation==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCorporation));
                                    } if (localCountryCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "countryCode"));
                                 
                                         elementList.add(localCountryCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCountryCode));
                                    } if (localCustomerNoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "customerNo"));
                                 
                                         elementList.add(localCustomerNo==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerNo));
                                    } if (localDepartment1Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "department1"));
                                 
                                         elementList.add(localDepartment1==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDepartment1));
                                    } if (localDepartment2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "department2"));
                                 
                                         elementList.add(localDepartment2==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDepartment2));
                                    } if (localE_Mail1Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "e_Mail1"));
                                 
                                         elementList.add(localE_Mail1==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localE_Mail1));
                                    } if (localE_Mail2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "e_Mail2"));
                                 
                                         elementList.add(localE_Mail2==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localE_Mail2));
                                    } if (localFaxTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "fax"));
                                 
                                         elementList.add(localFax==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFax));
                                    } if (localFax1Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "fax1"));
                                 
                                         elementList.add(localFax1==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFax1));
                                    } if (localFax2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "fax2"));
                                 
                                         elementList.add(localFax2==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFax2));
                                    } if (localFoundDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "foundDate"));
                                 
                                         elementList.add(localFoundDate==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFoundDate));
                                    } if (localGetFlagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "getFlag"));
                                 
                                         elementList.add(localGetFlag==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGetFlag));
                                    } if (localHeadShip1Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "headShip1"));
                                 
                                         elementList.add(localHeadShip1==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHeadShip1));
                                    } if (localHeadShip2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "headShip2"));
                                 
                                         elementList.add(localHeadShip2==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHeadShip2));
                                    } if (localHotlineTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "hotline"));
                                 
                                         elementList.add(localHotline==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHotline));
                                    } if (localInsAddressTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "insAddress"));
                                 
                                         elementList.add(localInsAddress==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInsAddress));
                                    } if (localInsAddressCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "insAddressCode"));
                                 
                                         elementList.add(localInsAddressCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInsAddressCode));
                                    } if (localInsCategoryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "insCategory"));
                                 
                                         elementList.add(localInsCategory==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInsCategory));
                                    } if (localInsClassTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "insClass"));
                                 
                                         elementList.add(localInsClass==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInsClass));
                                    } if (localInsSuperCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "insSuperCode"));
                                 
                                         elementList.add(localInsSuperCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInsSuperCode));
                                    } if (localInsTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "insType"));
                                 
                                         elementList.add(localInsType==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInsType));
                                    } if (localInsZipCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "insZipCode"));
                                 
                                         elementList.add(localInsZipCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInsZipCode));
                                    } if (localLinkCustomerNo1Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "linkCustomerNo1"));
                                 
                                         elementList.add(localLinkCustomerNo1==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLinkCustomerNo1));
                                    } if (localLinkCustomerNo2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "linkCustomerNo2"));
                                 
                                         elementList.add(localLinkCustomerNo2==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLinkCustomerNo2));
                                    } if (localLinkCustomerNo3Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "linkCustomerNo3"));
                                 
                                         elementList.add(localLinkCustomerNo3==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLinkCustomerNo3));
                                    } if (localLinkCustomerNo4Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "linkCustomerNo4"));
                                 
                                         elementList.add(localLinkCustomerNo4==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLinkCustomerNo4));
                                    } if (localLinkMan1Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "linkMan1"));
                                 
                                         elementList.add(localLinkMan1==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLinkMan1));
                                    } if (localLinkMan2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "linkMan2"));
                                 
                                         elementList.add(localLinkMan2==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLinkMan2));
                                    } if (localLinkMan3Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "linkMan3"));
                                 
                                         elementList.add(localLinkMan3==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLinkMan3));
                                    } if (localLinkMan4Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "linkMan4"));
                                 
                                         elementList.add(localLinkMan4==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLinkMan4));
                                    } if (localLogoRealPathTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "logoRealPath"));
                                 
                                         elementList.add(localLogoRealPath==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLogoRealPath));
                                    } if (localLogoRelaPathTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "logoRelaPath"));
                                 
                                         elementList.add(localLogoRelaPath==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLogoRelaPath));
                                    } if (localMainBussinessTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "mainBussiness"));
                                 
                                         elementList.add(localMainBussiness==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMainBussiness));
                                    } if (localMakeDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "makeDate"));
                                 
                                         elementList.add(localMakeDate==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMakeDate));
                                    } if (localMakeTimeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "makeTime"));
                                 
                                         elementList.add(localMakeTime==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMakeTime));
                                    } if (localMasterInscomTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "masterInscom"));
                                 
                                         elementList.add(localMasterInscom==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMasterInscom));
                                    } if (localMobile1Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "mobile1"));
                                 
                                         elementList.add(localMobile1==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMobile1));
                                    } if (localMobile2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "mobile2"));
                                 
                                         elementList.add(localMobile2==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMobile2));
                                    } if (localMobile3Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "mobile3"));
                                 
                                         elementList.add(localMobile3==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMobile3));
                                    } if (localMobile4Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "mobile4"));
                                 
                                         elementList.add(localMobile4==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMobile4));
                                    } if (localModifyDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "modifyDate"));
                                 
                                         elementList.add(localModifyDate==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localModifyDate));
                                    } if (localModifyTimeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "modifyTime"));
                                 
                                         elementList.add(localModifyTime==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localModifyTime));
                                    } if (localNetProfitRateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "netProfitRate"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNetProfitRate));
                            } if (localOperatorTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "operator"));
                                 
                                         elementList.add(localOperator==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOperator));
                                    } if (localPasswordTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "password"));
                                 
                                         elementList.add(localPassword==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassword));
                                    } if (localPeoplesTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "peoples"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPeoples));
                            } if (localPhoneTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "phone"));
                                 
                                         elementList.add(localPhone==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPhone));
                                    } if (localPhone1Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "phone1"));
                                 
                                         elementList.add(localPhone1==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPhone1));
                                    } if (localPhone2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "phone2"));
                                 
                                         elementList.add(localPhone2==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPhone2));
                                    } if (localProvinceCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "provinceCode"));
                                 
                                         elementList.add(localProvinceCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProvinceCode));
                                    } if (localRemarkTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "remark"));
                                 
                                         elementList.add(localRemark==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRemark));
                                    } if (localRgtMoneyTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "rgtMoney"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRgtMoney));
                            } if (localSatrapTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "satrap"));
                                 
                                         elementList.add(localSatrap==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSatrap));
                                    } if (localShortNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "shortName"));
                                 
                                         elementList.add(localShortName==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localShortName));
                                    } if (localStatusTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "status"));
                                 
                                         elementList.add(localStatus==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatus));
                                    } if (localStopFlgTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "stopFlg"));
                                 
                                         elementList.add(localStopFlg==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStopFlg));
                                    } if (localSupplierCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "supplierCode"));
                                 
                                         elementList.add(localSupplierCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSupplierCode));
                                    } if (localSupplierDescTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "supplierDesc"));
                                 
                                         elementList.add(localSupplierDesc==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSupplierDesc));
                                    } if (localSupplierEngNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "supplierEngName"));
                                 
                                         elementList.add(localSupplierEngName==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSupplierEngName));
                                    } if (localSupplierNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "supplierName"));
                                 
                                         elementList.add(localSupplierName==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSupplierName));
                                    } if (localSupplierShowCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "supplierShowCode"));
                                 
                                         elementList.add(localSupplierShowCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSupplierShowCode));
                                    } if (localSynchroFlagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "synchroFlag"));
                                 
                                         elementList.add(localSynchroFlag==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSynchroFlag));
                                    } if (localWebSiteTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "webSite"));
                                 
                                         elementList.add(localWebSite==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWebSite));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static FDInsCom parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            FDInsCom object =
                new FDInsCom();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"FDInsCom".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (FDInsCom)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list2 = new java.util.ArrayList();
                    
                        java.util.ArrayList list3 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","EMail").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEMail(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","FMClaimDataTitle").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list2.add(null);
                                                              reader.next();
                                                          } else {
                                                        list2.add(FMClaimDataTitle.Factory.parse(reader));
                                                                }
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone2 = false;
                                                        while(!loopDone2){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone2 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","FMClaimDataTitle").equals(reader.getName())){
                                                                    
                                                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                                          list2.add(null);
                                                                          reader.next();
                                                                      } else {
                                                                    list2.add(FMClaimDataTitle.Factory.parse(reader));
                                                                        }
                                                                }else{
                                                                    loopDone2 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setFMClaimDataTitle((FMClaimDataTitle[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                FMClaimDataTitle.class,
                                                                list2));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","FMClaimGuide").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list3.add(null);
                                                              reader.next();
                                                          } else {
                                                        list3.add(FMClaimGuide.Factory.parse(reader));
                                                                }
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone3 = false;
                                                        while(!loopDone3){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone3 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","FMClaimGuide").equals(reader.getName())){
                                                                    
                                                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                                          list3.add(null);
                                                                          reader.next();
                                                                      } else {
                                                                    list3.add(FMClaimGuide.Factory.parse(reader));
                                                                        }
                                                                }else{
                                                                    loopDone3 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setFMClaimGuide((FMClaimGuide[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                FMClaimGuide.class,
                                                                list3));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","areaCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAreaCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","asset").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAsset(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setAsset(java.lang.Double.NaN);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","autoRenewFlag").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAutoRenewFlag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","bankAccNo").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBankAccNo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","bankAddress").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBankAddress(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","bankCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBankCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","blacklistFlag").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBlacklistFlag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","cityCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCityCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","comAera").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setComAera(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","companyDesc").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCompanyDesc(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","corporation").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCorporation(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","countryCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCountryCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","customerNo").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerNo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","department1").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDepartment1(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","department2").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDepartment2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","e_Mail1").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setE_Mail1(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","e_Mail2").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setE_Mail2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","fax").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFax(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","fax1").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFax1(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","fax2").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFax2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","foundDate").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFoundDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","getFlag").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setGetFlag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","headShip1").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHeadShip1(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","headShip2").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHeadShip2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","hotline").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHotline(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","insAddress").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInsAddress(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","insAddressCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInsAddressCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","insCategory").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInsCategory(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","insClass").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInsClass(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","insSuperCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInsSuperCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","insType").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInsType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","insZipCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInsZipCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","linkCustomerNo1").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLinkCustomerNo1(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","linkCustomerNo2").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLinkCustomerNo2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","linkCustomerNo3").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLinkCustomerNo3(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","linkCustomerNo4").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLinkCustomerNo4(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","linkMan1").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLinkMan1(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","linkMan2").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLinkMan2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","linkMan3").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLinkMan3(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","linkMan4").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLinkMan4(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","logoRealPath").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLogoRealPath(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","logoRelaPath").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLogoRelaPath(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","mainBussiness").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMainBussiness(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","makeDate").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMakeDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","makeTime").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMakeTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","masterInscom").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMasterInscom(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","mobile1").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMobile1(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","mobile2").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMobile2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","mobile3").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMobile3(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","mobile4").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMobile4(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","modifyDate").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setModifyDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","modifyTime").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setModifyTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","netProfitRate").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNetProfitRate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setNetProfitRate(java.lang.Double.NaN);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","operator").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOperator(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","password").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPassword(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","peoples").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPeoples(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setPeoples(java.lang.Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","phone").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPhone(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","phone1").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPhone1(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","phone2").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPhone2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","provinceCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProvinceCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","remark").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRemark(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","rgtMoney").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRgtMoney(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setRgtMoney(java.lang.Double.NaN);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","satrap").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSatrap(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","shortName").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setShortName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","status").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStatus(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","stopFlg").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStopFlg(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","supplierCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSupplierCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","supplierDesc").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSupplierDesc(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","supplierEngName").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSupplierEngName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","supplierName").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSupplierName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","supplierShowCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSupplierShowCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","synchroFlag").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSynchroFlag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","webSite").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWebSite(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class FMClaimDataTitle
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = FMClaimDataTitle
                Namespace URI = http://pojo.webService.ebusiness.sinosoft.com/xsd
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for Company
                        */

                        
                                    protected java.lang.String localCompany ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCompanyTracker = false ;

                           public boolean isCompanySpecified(){
                               return localCompanyTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCompany(){
                               return localCompany;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Company
                               */
                               public void setCompany(java.lang.String param){
                            localCompanyTracker = true;
                                   
                                            this.localCompany=param;
                                    

                               }
                            

                        /**
                        * field for Id
                        */

                        
                                    protected java.lang.String localId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIdTracker = false ;

                           public boolean isIdSpecified(){
                               return localIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getId(){
                               return localId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Id
                               */
                               public void setId(java.lang.String param){
                            localIdTracker = true;
                                   
                                            this.localId=param;
                                    

                               }
                            

                        /**
                        * field for MFMClaimDataList
                        * This was an Array!
                        */

                        
                                    protected FMClaimData[] localMFMClaimDataList ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMFMClaimDataListTracker = false ;

                           public boolean isMFMClaimDataListSpecified(){
                               return localMFMClaimDataListTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return FMClaimData[]
                           */
                           public  FMClaimData[] getMFMClaimDataList(){
                               return localMFMClaimDataList;
                           }

                           
                        


                               
                              /**
                               * validate the array for MFMClaimDataList
                               */
                              protected void validateMFMClaimDataList(FMClaimData[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param MFMClaimDataList
                              */
                              public void setMFMClaimDataList(FMClaimData[] param){
                              
                                   validateMFMClaimDataList(param);

                               localMFMClaimDataListTracker = true;
                                      
                                      this.localMFMClaimDataList=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param FMClaimData
                             */
                             public void addMFMClaimDataList(FMClaimData param){
                                   if (localMFMClaimDataList == null){
                                   localMFMClaimDataList = new FMClaimData[]{};
                                   }

                            
                                 //update the setting tracker
                                localMFMClaimDataListTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localMFMClaimDataList);
                               list.add(param);
                               this.localMFMClaimDataList =
                             (FMClaimData[])list.toArray(
                            new FMClaimData[list.size()]);

                             }
                             

                        /**
                        * field for Ordervalue
                        */

                        
                                    protected java.lang.String localOrdervalue ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOrdervalueTracker = false ;

                           public boolean isOrdervalueSpecified(){
                               return localOrdervalueTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOrdervalue(){
                               return localOrdervalue;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Ordervalue
                               */
                               public void setOrdervalue(java.lang.String param){
                            localOrdervalueTracker = true;
                                   
                                            this.localOrdervalue=param;
                                    

                               }
                            

                        /**
                        * field for Prop1
                        */

                        
                                    protected java.lang.String localProp1 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProp1Tracker = false ;

                           public boolean isProp1Specified(){
                               return localProp1Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProp1(){
                               return localProp1;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Prop1
                               */
                               public void setProp1(java.lang.String param){
                            localProp1Tracker = true;
                                   
                                            this.localProp1=param;
                                    

                               }
                            

                        /**
                        * field for Prop2
                        */

                        
                                    protected java.lang.String localProp2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProp2Tracker = false ;

                           public boolean isProp2Specified(){
                               return localProp2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProp2(){
                               return localProp2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Prop2
                               */
                               public void setProp2(java.lang.String param){
                            localProp2Tracker = true;
                                   
                                            this.localProp2=param;
                                    

                               }
                            

                        /**
                        * field for Prop3
                        */

                        
                                    protected java.lang.String localProp3 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProp3Tracker = false ;

                           public boolean isProp3Specified(){
                               return localProp3Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProp3(){
                               return localProp3;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Prop3
                               */
                               public void setProp3(java.lang.String param){
                            localProp3Tracker = true;
                                   
                                            this.localProp3=param;
                                    

                               }
                            

                        /**
                        * field for Title
                        */

                        
                                    protected java.lang.String localTitle ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTitleTracker = false ;

                           public boolean isTitleSpecified(){
                               return localTitleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTitle(){
                               return localTitle;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Title
                               */
                               public void setTitle(java.lang.String param){
                            localTitleTracker = true;
                                   
                                            this.localTitle=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://pojo.webService.ebusiness.sinosoft.com/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":FMClaimDataTitle",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "FMClaimDataTitle",
                           xmlWriter);
                   }

               
                   }
                if (localCompanyTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "company", xmlWriter);
                             

                                          if (localCompany==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCompany);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localIdTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "id", xmlWriter);
                             

                                          if (localId==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMFMClaimDataListTracker){
                                       if (localMFMClaimDataList!=null){
                                            for (int i = 0;i < localMFMClaimDataList.length;i++){
                                                if (localMFMClaimDataList[i] != null){
                                                 localMFMClaimDataList[i].serialize(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","mFMClaimDataList"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                            writeStartElement(null, "http://pojo.webService.ebusiness.sinosoft.com/xsd", "mFMClaimDataList", xmlWriter);

                                                           // write the nil attribute
                                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                           xmlWriter.writeEndElement();
                                                    
                                                }

                                            }
                                     } else {
                                        
                                                writeStartElement(null, "http://pojo.webService.ebusiness.sinosoft.com/xsd", "mFMClaimDataList", xmlWriter);

                                               // write the nil attribute
                                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                               xmlWriter.writeEndElement();
                                        
                                    }
                                 } if (localOrdervalueTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "ordervalue", xmlWriter);
                             

                                          if (localOrdervalue==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOrdervalue);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localProp1Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "prop1", xmlWriter);
                             

                                          if (localProp1==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProp1);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localProp2Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "prop2", xmlWriter);
                             

                                          if (localProp2==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProp2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localProp3Tracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "prop3", xmlWriter);
                             

                                          if (localProp3==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProp3);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTitleTracker){
                                    namespace = "http://pojo.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "title", xmlWriter);
                             

                                          if (localTitle==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTitle);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://pojo.webService.ebusiness.sinosoft.com/xsd")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localCompanyTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "company"));
                                 
                                         elementList.add(localCompany==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCompany));
                                    } if (localIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "id"));
                                 
                                         elementList.add(localId==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId));
                                    } if (localMFMClaimDataListTracker){
                             if (localMFMClaimDataList!=null) {
                                 for (int i = 0;i < localMFMClaimDataList.length;i++){

                                    if (localMFMClaimDataList[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                          "mFMClaimDataList"));
                                         elementList.add(localMFMClaimDataList[i]);
                                    } else {
                                        
                                                elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                          "mFMClaimDataList"));
                                                elementList.add(null);
                                            
                                    }

                                 }
                             } else {
                                 
                                        elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                          "mFMClaimDataList"));
                                        elementList.add(localMFMClaimDataList);
                                    
                             }

                        } if (localOrdervalueTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "ordervalue"));
                                 
                                         elementList.add(localOrdervalue==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOrdervalue));
                                    } if (localProp1Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "prop1"));
                                 
                                         elementList.add(localProp1==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProp1));
                                    } if (localProp2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "prop2"));
                                 
                                         elementList.add(localProp2==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProp2));
                                    } if (localProp3Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "prop3"));
                                 
                                         elementList.add(localProp3==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProp3));
                                    } if (localTitleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd",
                                                                      "title"));
                                 
                                         elementList.add(localTitle==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTitle));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static FMClaimDataTitle parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            FMClaimDataTitle object =
                new FMClaimDataTitle();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"FMClaimDataTitle".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (FMClaimDataTitle)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list3 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","company").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCompany(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","id").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","mFMClaimDataList").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list3.add(null);
                                                              reader.next();
                                                          } else {
                                                        list3.add(FMClaimData.Factory.parse(reader));
                                                                }
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone3 = false;
                                                        while(!loopDone3){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone3 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","mFMClaimDataList").equals(reader.getName())){
                                                                    
                                                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                                          list3.add(null);
                                                                          reader.next();
                                                                      } else {
                                                                    list3.add(FMClaimData.Factory.parse(reader));
                                                                        }
                                                                }else{
                                                                    loopDone3 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setMFMClaimDataList((FMClaimData[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                FMClaimData.class,
                                                                list3));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","ordervalue").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOrdervalue(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","prop1").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProp1(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","prop2").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProp2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","prop3").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProp3(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pojo.webService.ebusiness.sinosoft.com/xsd","title").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTitle(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class FDInsComResponse
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = FDInsComResponse
                Namespace URI = http://model.webService.ebusiness.sinosoft.com/xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for FDInsComList
                        * This was an Array!
                        */

                        
                                    protected FDInsCom[] localFDInsComList ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFDInsComListTracker = false ;

                           public boolean isFDInsComListSpecified(){
                               return localFDInsComListTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return FDInsCom[]
                           */
                           public  FDInsCom[] getFDInsComList(){
                               return localFDInsComList;
                           }

                           
                        


                               
                              /**
                               * validate the array for FDInsComList
                               */
                              protected void validateFDInsComList(FDInsCom[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param FDInsComList
                              */
                              public void setFDInsComList(FDInsCom[] param){
                              
                                   validateFDInsComList(param);

                               localFDInsComListTracker = true;
                                      
                                      this.localFDInsComList=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param FDInsCom
                             */
                             public void addFDInsComList(FDInsCom param){
                                   if (localFDInsComList == null){
                                   localFDInsComList = new FDInsCom[]{};
                                   }

                            
                                 //update the setting tracker
                                localFDInsComListTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localFDInsComList);
                               list.add(param);
                               this.localFDInsComList =
                             (FDInsCom[])list.toArray(
                            new FDInsCom[list.size()]);

                             }
                             

                        /**
                        * field for ResponseDate
                        */

                        
                                    protected java.lang.String localResponseDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResponseDateTracker = false ;

                           public boolean isResponseDateSpecified(){
                               return localResponseDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResponseDate(){
                               return localResponseDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResponseDate
                               */
                               public void setResponseDate(java.lang.String param){
                            localResponseDateTracker = true;
                                   
                                            this.localResponseDate=param;
                                    

                               }
                            

                        /**
                        * field for ResponseExtension
                        */

                        
                                    protected ResponseExtension localResponseExtension ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResponseExtensionTracker = false ;

                           public boolean isResponseExtensionSpecified(){
                               return localResponseExtensionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return ResponseExtension
                           */
                           public  ResponseExtension getResponseExtension(){
                               return localResponseExtension;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResponseExtension
                               */
                               public void setResponseExtension(ResponseExtension param){
                            localResponseExtensionTracker = true;
                                   
                                            this.localResponseExtension=param;
                                    

                               }
                            

                        /**
                        * field for ResponseGUID
                        */

                        
                                    protected java.lang.String localResponseGUID ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResponseGUIDTracker = false ;

                           public boolean isResponseGUIDSpecified(){
                               return localResponseGUIDTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResponseGUID(){
                               return localResponseGUID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResponseGUID
                               */
                               public void setResponseGUID(java.lang.String param){
                            localResponseGUIDTracker = true;
                                   
                                            this.localResponseGUID=param;
                                    

                               }
                            

                        /**
                        * field for ResponseTime
                        */

                        
                                    protected java.lang.String localResponseTime ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResponseTimeTracker = false ;

                           public boolean isResponseTimeSpecified(){
                               return localResponseTimeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResponseTime(){
                               return localResponseTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResponseTime
                               */
                               public void setResponseTime(java.lang.String param){
                            localResponseTimeTracker = true;
                                   
                                            this.localResponseTime=param;
                                    

                               }
                            

                        /**
                        * field for ResponseType
                        */

                        
                                    protected java.lang.String localResponseType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResponseTypeTracker = false ;

                           public boolean isResponseTypeSpecified(){
                               return localResponseTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResponseType(){
                               return localResponseType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResponseType
                               */
                               public void setResponseType(java.lang.String param){
                            localResponseTypeTracker = true;
                                   
                                            this.localResponseType=param;
                                    

                               }
                            

                        /**
                        * field for ResultDTO
                        */

                        
                                    protected ResultDTO localResultDTO ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResultDTOTracker = false ;

                           public boolean isResultDTOSpecified(){
                               return localResultDTOTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return ResultDTO
                           */
                           public  ResultDTO getResultDTO(){
                               return localResultDTO;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResultDTO
                               */
                               public void setResultDTO(ResultDTO param){
                            localResultDTOTracker = true;
                                   
                                            this.localResultDTO=param;
                                    

                               }
                            

                        /**
                        * field for TransCheckInfo
                        */

                        
                                    protected TransCheckInfo localTransCheckInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTransCheckInfoTracker = false ;

                           public boolean isTransCheckInfoSpecified(){
                               return localTransCheckInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return TransCheckInfo
                           */
                           public  TransCheckInfo getTransCheckInfo(){
                               return localTransCheckInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TransCheckInfo
                               */
                               public void setTransCheckInfo(TransCheckInfo param){
                            localTransCheckInfoTracker = true;
                                   
                                            this.localTransCheckInfo=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://model.webService.ebusiness.sinosoft.com/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":FDInsComResponse",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "FDInsComResponse",
                           xmlWriter);
                   }

               
                   }
                if (localFDInsComListTracker){
                                       if (localFDInsComList!=null){
                                            for (int i = 0;i < localFDInsComList.length;i++){
                                                if (localFDInsComList[i] != null){
                                                 localFDInsComList[i].serialize(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","FDInsComList"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                            writeStartElement(null, "http://model.webService.ebusiness.sinosoft.com/xsd", "FDInsComList", xmlWriter);

                                                           // write the nil attribute
                                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                           xmlWriter.writeEndElement();
                                                    
                                                }

                                            }
                                     } else {
                                        
                                                writeStartElement(null, "http://model.webService.ebusiness.sinosoft.com/xsd", "FDInsComList", xmlWriter);

                                               // write the nil attribute
                                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                               xmlWriter.writeEndElement();
                                        
                                    }
                                 } if (localResponseDateTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "responseDate", xmlWriter);
                             

                                          if (localResponseDate==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResponseDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResponseExtensionTracker){
                                    if (localResponseExtension==null){

                                        writeStartElement(null, "http://model.webService.ebusiness.sinosoft.com/xsd", "responseExtension", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localResponseExtension.serialize(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","responseExtension"),
                                        xmlWriter);
                                    }
                                } if (localResponseGUIDTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "responseGUID", xmlWriter);
                             

                                          if (localResponseGUID==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResponseGUID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResponseTimeTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "responseTime", xmlWriter);
                             

                                          if (localResponseTime==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResponseTime);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResponseTypeTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "responseType", xmlWriter);
                             

                                          if (localResponseType==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResponseType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResultDTOTracker){
                                    if (localResultDTO==null){

                                        writeStartElement(null, "http://model.webService.ebusiness.sinosoft.com/xsd", "resultDTO", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localResultDTO.serialize(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","resultDTO"),
                                        xmlWriter);
                                    }
                                } if (localTransCheckInfoTracker){
                                    if (localTransCheckInfo==null){

                                        writeStartElement(null, "http://model.webService.ebusiness.sinosoft.com/xsd", "transCheckInfo", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localTransCheckInfo.serialize(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","transCheckInfo"),
                                        xmlWriter);
                                    }
                                }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://model.webService.ebusiness.sinosoft.com/xsd")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localFDInsComListTracker){
                             if (localFDInsComList!=null) {
                                 for (int i = 0;i < localFDInsComList.length;i++){

                                    if (localFDInsComList[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                          "FDInsComList"));
                                         elementList.add(localFDInsComList[i]);
                                    } else {
                                        
                                                elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                          "FDInsComList"));
                                                elementList.add(null);
                                            
                                    }

                                 }
                             } else {
                                 
                                        elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                          "FDInsComList"));
                                        elementList.add(localFDInsComList);
                                    
                             }

                        } if (localResponseDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "responseDate"));
                                 
                                         elementList.add(localResponseDate==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResponseDate));
                                    } if (localResponseExtensionTracker){
                            elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "responseExtension"));
                            
                            
                                    elementList.add(localResponseExtension==null?null:
                                    localResponseExtension);
                                } if (localResponseGUIDTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "responseGUID"));
                                 
                                         elementList.add(localResponseGUID==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResponseGUID));
                                    } if (localResponseTimeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "responseTime"));
                                 
                                         elementList.add(localResponseTime==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResponseTime));
                                    } if (localResponseTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "responseType"));
                                 
                                         elementList.add(localResponseType==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResponseType));
                                    } if (localResultDTOTracker){
                            elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "resultDTO"));
                            
                            
                                    elementList.add(localResultDTO==null?null:
                                    localResultDTO);
                                } if (localTransCheckInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "transCheckInfo"));
                            
                            
                                    elementList.add(localTransCheckInfo==null?null:
                                    localTransCheckInfo);
                                }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static FDInsComResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            FDInsComResponse object =
                new FDInsComResponse();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"FDInsComResponse".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (FDInsComResponse)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list1 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","FDInsComList").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list1.add(null);
                                                              reader.next();
                                                          } else {
                                                        list1.add(FDInsCom.Factory.parse(reader));
                                                                }
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone1 = false;
                                                        while(!loopDone1){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone1 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","FDInsComList").equals(reader.getName())){
                                                                    
                                                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                                          list1.add(null);
                                                                          reader.next();
                                                                      } else {
                                                                    list1.add(FDInsCom.Factory.parse(reader));
                                                                        }
                                                                }else{
                                                                    loopDone1 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setFDInsComList((FDInsCom[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                FDInsCom.class,
                                                                list1));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","responseDate").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResponseDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","responseExtension").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setResponseExtension(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setResponseExtension(ResponseExtension.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","responseGUID").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResponseGUID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","responseTime").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResponseTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","responseType").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResponseType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","resultDTO").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setResultDTO(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setResultDTO(ResultDTO.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","transCheckInfo").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setTransCheckInfo(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setTransCheckInfo(TransCheckInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://model.webService.ebusiness.sinosoft.com/xsd".equals(namespaceURI) &&
                  "RequestExtension".equals(typeName)){
                   
                            return  RequestExtension.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://pojo.webService.ebusiness.sinosoft.com/xsd".equals(namespaceURI) &&
                  "FMClaimGuide".equals(typeName)){
                   
                            return  FMClaimGuide.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://pojo.webService.ebusiness.sinosoft.com/xsd".equals(namespaceURI) &&
                  "FMClaimData".equals(typeName)){
                   
                            return  FMClaimData.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://model.webService.ebusiness.sinosoft.com/xsd".equals(namespaceURI) &&
                  "ResponseExtension".equals(typeName)){
                   
                            return  ResponseExtension.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://model.webService.ebusiness.sinosoft.com/xsd".equals(namespaceURI) &&
                  "TransCheckInfo".equals(typeName)){
                   
                            return  TransCheckInfo.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://model.webService.ebusiness.sinosoft.com/xsd".equals(namespaceURI) &&
                  "ResultDTO".equals(typeName)){
                   
                            return  ResultDTO.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://model.webService.ebusiness.sinosoft.com/xsd".equals(namespaceURI) &&
                  "FDInsComRequest".equals(typeName)){
                   
                            return  FDInsComRequest.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://pojo.webService.ebusiness.sinosoft.com/xsd".equals(namespaceURI) &&
                  "FDInsCom".equals(typeName)){
                   
                            return  FDInsCom.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://model.webService.ebusiness.sinosoft.com/xsd".equals(namespaceURI) &&
                  "FDInsComResponse".equals(typeName)){
                   
                            return  FDInsComResponse.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://pojo.webService.ebusiness.sinosoft.com/xsd".equals(namespaceURI) &&
                  "FMClaimDataTitle".equals(typeName)){
                   
                            return  FMClaimDataTitle.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    
        public static class ResponseExtension
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = ResponseExtension
                Namespace URI = http://model.webService.ebusiness.sinosoft.com/xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for MaxRecords
                        */

                        
                                    protected java.lang.String localMaxRecords ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMaxRecordsTracker = false ;

                           public boolean isMaxRecordsSpecified(){
                               return localMaxRecordsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMaxRecords(){
                               return localMaxRecords;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MaxRecords
                               */
                               public void setMaxRecords(java.lang.String param){
                            localMaxRecordsTracker = true;
                                   
                                            this.localMaxRecords=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://model.webService.ebusiness.sinosoft.com/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":ResponseExtension",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "ResponseExtension",
                           xmlWriter);
                   }

               
                   }
                if (localMaxRecordsTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "maxRecords", xmlWriter);
                             

                                          if (localMaxRecords==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMaxRecords);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://model.webService.ebusiness.sinosoft.com/xsd")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localMaxRecordsTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "maxRecords"));
                                 
                                         elementList.add(localMaxRecords==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxRecords));
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static ResponseExtension parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            ResponseExtension object =
                new ResponseExtension();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"ResponseExtension".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (ResponseExtension)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","maxRecords").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMaxRecords(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class GetFDInsComResponse
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://service.webService.ebusiness.sinosoft.com",
                "getFDInsComResponse",
                "ns3");

            

                        /**
                        * field for _return
                        */

                        
                                    protected FDInsComResponse local_return ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean local_returnTracker = false ;

                           public boolean is_returnSpecified(){
                               return local_returnTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return FDInsComResponse
                           */
                           public  FDInsComResponse get_return(){
                               return local_return;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param _return
                               */
                               public void set_return(FDInsComResponse param){
                            local_returnTracker = true;
                                   
                                            this.local_return=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME);
               return factory.createOMElement(dataSource,MY_QNAME);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://service.webService.ebusiness.sinosoft.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getFDInsComResponse",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getFDInsComResponse",
                           xmlWriter);
                   }

               
                   }
                if (local_returnTracker){
                                    if (local_return==null){

                                        writeStartElement(null, "http://service.webService.ebusiness.sinosoft.com", "return", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     local_return.serialize(new javax.xml.namespace.QName("http://service.webService.ebusiness.sinosoft.com","return"),
                                        xmlWriter);
                                    }
                                }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://service.webService.ebusiness.sinosoft.com")){
                return "ns3";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (local_returnTracker){
                            elementList.add(new javax.xml.namespace.QName("http://service.webService.ebusiness.sinosoft.com",
                                                                      "return"));
                            
                            
                                    elementList.add(local_return==null?null:
                                    local_return);
                                }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static GetFDInsComResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetFDInsComResponse object =
                new GetFDInsComResponse();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"getFDInsComResponse".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetFDInsComResponse)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://service.webService.ebusiness.sinosoft.com","return").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.set_return(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.set_return(FDInsComResponse.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class FDInsComRequest
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = FDInsComRequest
                Namespace URI = http://model.webService.ebusiness.sinosoft.com/xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for ERiskType
                        */

                        
                                    protected java.lang.String localERiskType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localERiskTypeTracker = false ;

                           public boolean isERiskTypeSpecified(){
                               return localERiskTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getERiskType(){
                               return localERiskType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ERiskType
                               */
                               public void setERiskType(java.lang.String param){
                            localERiskTypeTracker = true;
                                   
                                            this.localERiskType=param;
                                    

                               }
                            

                        /**
                        * field for InsType
                        */

                        
                                    protected java.lang.String localInsType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInsTypeTracker = false ;

                           public boolean isInsTypeSpecified(){
                               return localInsTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInsType(){
                               return localInsType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InsType
                               */
                               public void setInsType(java.lang.String param){
                            localInsTypeTracker = true;
                                   
                                            this.localInsType=param;
                                    

                               }
                            

                        /**
                        * field for RequestDate
                        */

                        
                                    protected java.lang.String localRequestDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRequestDateTracker = false ;

                           public boolean isRequestDateSpecified(){
                               return localRequestDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRequestDate(){
                               return localRequestDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RequestDate
                               */
                               public void setRequestDate(java.lang.String param){
                            localRequestDateTracker = true;
                                   
                                            this.localRequestDate=param;
                                    

                               }
                            

                        /**
                        * field for RequestExtension
                        */

                        
                                    protected RequestExtension localRequestExtension ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRequestExtensionTracker = false ;

                           public boolean isRequestExtensionSpecified(){
                               return localRequestExtensionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return RequestExtension
                           */
                           public  RequestExtension getRequestExtension(){
                               return localRequestExtension;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RequestExtension
                               */
                               public void setRequestExtension(RequestExtension param){
                            localRequestExtensionTracker = true;
                                   
                                            this.localRequestExtension=param;
                                    

                               }
                            

                        /**
                        * field for RequestGUID
                        */

                        
                                    protected java.lang.String localRequestGUID ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRequestGUIDTracker = false ;

                           public boolean isRequestGUIDSpecified(){
                               return localRequestGUIDTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRequestGUID(){
                               return localRequestGUID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RequestGUID
                               */
                               public void setRequestGUID(java.lang.String param){
                            localRequestGUIDTracker = true;
                                   
                                            this.localRequestGUID=param;
                                    

                               }
                            

                        /**
                        * field for RequestTime
                        */

                        
                                    protected java.lang.String localRequestTime ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRequestTimeTracker = false ;

                           public boolean isRequestTimeSpecified(){
                               return localRequestTimeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRequestTime(){
                               return localRequestTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RequestTime
                               */
                               public void setRequestTime(java.lang.String param){
                            localRequestTimeTracker = true;
                                   
                                            this.localRequestTime=param;
                                    

                               }
                            

                        /**
                        * field for RequestType
                        */

                        
                                    protected java.lang.String localRequestType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRequestTypeTracker = false ;

                           public boolean isRequestTypeSpecified(){
                               return localRequestTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRequestType(){
                               return localRequestType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RequestType
                               */
                               public void setRequestType(java.lang.String param){
                            localRequestTypeTracker = true;
                                   
                                            this.localRequestType=param;
                                    

                               }
                            

                        /**
                        * field for RiskCode
                        */

                        
                                    protected java.lang.String localRiskCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRiskCodeTracker = false ;

                           public boolean isRiskCodeSpecified(){
                               return localRiskCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRiskCode(){
                               return localRiskCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RiskCode
                               */
                               public void setRiskCode(java.lang.String param){
                            localRiskCodeTracker = true;
                                   
                                            this.localRiskCode=param;
                                    

                               }
                            

                        /**
                        * field for RiskName
                        */

                        
                                    protected java.lang.String localRiskName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRiskNameTracker = false ;

                           public boolean isRiskNameSpecified(){
                               return localRiskNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRiskName(){
                               return localRiskName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RiskName
                               */
                               public void setRiskName(java.lang.String param){
                            localRiskNameTracker = true;
                                   
                                            this.localRiskName=param;
                                    

                               }
                            

                        /**
                        * field for SupplierCode
                        */

                        
                                    protected java.lang.String localSupplierCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSupplierCodeTracker = false ;

                           public boolean isSupplierCodeSpecified(){
                               return localSupplierCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSupplierCode(){
                               return localSupplierCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SupplierCode
                               */
                               public void setSupplierCode(java.lang.String param){
                            localSupplierCodeTracker = true;
                                   
                                            this.localSupplierCode=param;
                                    

                               }
                            

                        /**
                        * field for SupplierName
                        */

                        
                                    protected java.lang.String localSupplierName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSupplierNameTracker = false ;

                           public boolean isSupplierNameSpecified(){
                               return localSupplierNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSupplierName(){
                               return localSupplierName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SupplierName
                               */
                               public void setSupplierName(java.lang.String param){
                            localSupplierNameTracker = true;
                                   
                                            this.localSupplierName=param;
                                    

                               }
                            

                        /**
                        * field for TransCheckInfo
                        */

                        
                                    protected TransCheckInfo localTransCheckInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTransCheckInfoTracker = false ;

                           public boolean isTransCheckInfoSpecified(){
                               return localTransCheckInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return TransCheckInfo
                           */
                           public  TransCheckInfo getTransCheckInfo(){
                               return localTransCheckInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TransCheckInfo
                               */
                               public void setTransCheckInfo(TransCheckInfo param){
                            localTransCheckInfoTracker = true;
                                   
                                            this.localTransCheckInfo=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://model.webService.ebusiness.sinosoft.com/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":FDInsComRequest",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "FDInsComRequest",
                           xmlWriter);
                   }

               
                   }
                if (localERiskTypeTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "ERiskType", xmlWriter);
                             

                                          if (localERiskType==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localERiskType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localInsTypeTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "insType", xmlWriter);
                             

                                          if (localInsType==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInsType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRequestDateTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "requestDate", xmlWriter);
                             

                                          if (localRequestDate==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRequestDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRequestExtensionTracker){
                                    if (localRequestExtension==null){

                                        writeStartElement(null, "http://model.webService.ebusiness.sinosoft.com/xsd", "requestExtension", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localRequestExtension.serialize(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","requestExtension"),
                                        xmlWriter);
                                    }
                                } if (localRequestGUIDTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "requestGUID", xmlWriter);
                             

                                          if (localRequestGUID==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRequestGUID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRequestTimeTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "requestTime", xmlWriter);
                             

                                          if (localRequestTime==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRequestTime);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRequestTypeTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "requestType", xmlWriter);
                             

                                          if (localRequestType==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRequestType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRiskCodeTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "riskCode", xmlWriter);
                             

                                          if (localRiskCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRiskCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRiskNameTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "riskName", xmlWriter);
                             

                                          if (localRiskName==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRiskName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSupplierCodeTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "supplierCode", xmlWriter);
                             

                                          if (localSupplierCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSupplierCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSupplierNameTracker){
                                    namespace = "http://model.webService.ebusiness.sinosoft.com/xsd";
                                    writeStartElement(null, namespace, "supplierName", xmlWriter);
                             

                                          if (localSupplierName==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSupplierName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTransCheckInfoTracker){
                                    if (localTransCheckInfo==null){

                                        writeStartElement(null, "http://model.webService.ebusiness.sinosoft.com/xsd", "transCheckInfo", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localTransCheckInfo.serialize(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","transCheckInfo"),
                                        xmlWriter);
                                    }
                                }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://model.webService.ebusiness.sinosoft.com/xsd")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localERiskTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "ERiskType"));
                                 
                                         elementList.add(localERiskType==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localERiskType));
                                    } if (localInsTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "insType"));
                                 
                                         elementList.add(localInsType==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInsType));
                                    } if (localRequestDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "requestDate"));
                                 
                                         elementList.add(localRequestDate==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRequestDate));
                                    } if (localRequestExtensionTracker){
                            elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "requestExtension"));
                            
                            
                                    elementList.add(localRequestExtension==null?null:
                                    localRequestExtension);
                                } if (localRequestGUIDTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "requestGUID"));
                                 
                                         elementList.add(localRequestGUID==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRequestGUID));
                                    } if (localRequestTimeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "requestTime"));
                                 
                                         elementList.add(localRequestTime==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRequestTime));
                                    } if (localRequestTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "requestType"));
                                 
                                         elementList.add(localRequestType==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRequestType));
                                    } if (localRiskCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "riskCode"));
                                 
                                         elementList.add(localRiskCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRiskCode));
                                    } if (localRiskNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "riskName"));
                                 
                                         elementList.add(localRiskName==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRiskName));
                                    } if (localSupplierCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "supplierCode"));
                                 
                                         elementList.add(localSupplierCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSupplierCode));
                                    } if (localSupplierNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "supplierName"));
                                 
                                         elementList.add(localSupplierName==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSupplierName));
                                    } if (localTransCheckInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd",
                                                                      "transCheckInfo"));
                            
                            
                                    elementList.add(localTransCheckInfo==null?null:
                                    localTransCheckInfo);
                                }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static FDInsComRequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            FDInsComRequest object =
                new FDInsComRequest();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"FDInsComRequest".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (FDInsComRequest)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","ERiskType").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setERiskType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","insType").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInsType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","requestDate").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRequestDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","requestExtension").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setRequestExtension(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setRequestExtension(RequestExtension.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","requestGUID").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRequestGUID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","requestTime").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRequestTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","requestType").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRequestType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","riskCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRiskCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","riskName").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRiskName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","supplierCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSupplierCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","supplierName").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSupplierName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://model.webService.ebusiness.sinosoft.com/xsd","transCheckInfo").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setTransCheckInfo(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setTransCheckInfo(TransCheckInfo.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
            private  org.apache.axiom.om.OMElement  toOM(com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsCom param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsCom.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsComResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsComResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsCom param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsCom.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsCom.class.equals(type)){
                
                           return com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsCom.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsComResponse.class.equals(type)){
                
                           return com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.GetFDInsComResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    
   }
   