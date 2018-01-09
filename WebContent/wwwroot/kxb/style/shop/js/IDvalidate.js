 

/**  
 * ���֤15λ�������dddddd yymmdd xx p   
 * dddddd��������   
 * yymmdd: ����������   
 * xx: ˳������룬�޷�ȷ��   
 * p: �Ա�����Ϊ�У�ż��ΪŮ  
 * <p />  
 * ���֤18λ�������dddddd yyyymmdd xxx y   
 * dddddd��������   
 * yyyymmdd: ����������   
 * xxx:˳������룬�޷�ȷ��������Ϊ�У�ż��ΪŮ   
 * y: У���룬��λ��ֵ��ͨ��ǰ17λ������  
 * <p />  
 * 18λ�����Ȩ����Ϊ(���ҵ���) Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,1 ]  
 * ��֤λ Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]   
 * У��λ���㹫ʽ��Y_P = mod( ��(Ai��Wi),11 )   
 * iΪ���֤�������������� 2...18 λ; Y_PΪ��ѾУ��������У��������λ��  
 *   
 */  
  
var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// ��Ȩ����   
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// ���֤��֤λֵ.10���X   
function IdCardValidate(idCard) {   
    idCard = trim(idCard.replace(/ /g, ""));   
    if (idCard.length == 15) {   
        return false;   
    } else if (idCard.length == 18) {   
        var a_idCard = idCard.split("");// �õ����֤����   
        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   
            return true;   
        }else {   
            return false;   
        }   
    } else {   
        return false;   
    }   
}   
/**  
 * �ж����֤����Ϊ18λʱ������֤λ�Ƿ���ȷ  
 * @param a_idCard ���֤��������  
 * @return  
 */  
function isTrueValidateCodeBy18IdCard(a_idCard) {   
    var sum = 0; // �����Ȩ��ͱ�   
    if (a_idCard[17].toLowerCase() == 'x') {   
        a_idCard[17] = 10;// �����λΪx����֤���滻Ϊ10����������   
    }   
    for ( var i = 0; i < 17; i++) {   
        sum += Wi[i] * a_idCard[i];// ��Ȩ���   
    }   
    valCodePosition = sum % 11;// �õ���֤����λ��   
    if (a_idCard[17] == ValideCode[valCodePosition]) {   
        return true;   
    } else {   
        return false;   
    }   
}   
/**  
 * ͨ�����֤�ж�������Ů  
 * @param idCard 15/18λ���֤����   
 * @return 'female'-Ů��'male'-��  
 */  
function maleOrFemalByIdCard(idCard){   
    idCard = trim(idCard.replace(/ /g, ""));// �����֤�������?��(�ַ���пո�   
    if(idCard.length==15){   
        if(idCard.substring(14,15)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else if(idCard.length ==18){   
        if(idCard.substring(14,17)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else{   
        return null;   
    }   
//  �ɶԴ����ַ�ֱ�ӵ�������4����   
// if(idCard.length==15){   
// alert(idCard[13]);   
// if(idCard[13]%2==0){   
// return 'female';   
// }else{   
// return 'male';   
// }   
// }else if(idCard.length==18){   
// alert(idCard[16]);   
// if(idCard[16]%2==0){   
// return 'female';   
// }else{   
// return 'male';   
// }   
// }else{   
// return null;   
// }   
}   
 /**  
  * ��֤18λ�����֤�����е������Ƿ�����Ч����  
  * @param idCard 18λ�����֤�ַ�  
  * @return  
  */  
function isValidityBrithBy18IdCard(idCard18){   
    var year =  idCard18.substring(6,10);   
    var month = idCard18.substring(10,12);   
    var day = idCard18.substring(12,14);   
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
    // ������getFullYear()��ȡ��ݣ�����ǧ�������   
    if(temp_date.getFullYear()!=parseFloat(year)   
          ||temp_date.getMonth()!=parseFloat(month)-1   
          ||temp_date.getDate()!=parseFloat(day)){   
            return false;   
    }else{   
        return true;   
    }   
}   
  /**  
   * ��֤15λ�����֤�����е������Ƿ�����Ч����  
   * @param idCard15 15λ�����֤�ַ�  
   * @return  
   */  
  function isValidityBrithBy15IdCard(idCard15){   
      var year =  idCard15.substring(6,8);   
      var month = idCard15.substring(8,10);   
      var day = idCard15.substring(10,12);   
      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
      // ���������֤�е����������迼��ǧ��������ʹ��getYear()����   
      if(temp_date.getYear()!=parseFloat(year)   
              ||temp_date.getMonth()!=parseFloat(month)-1   
              ||temp_date.getDate()!=parseFloat(day)){   
                return false;   
        }else{   
            return true;   
        }   
  }   
//ȥ���ַ�ͷβ�ո�   
function trim(str) {   
    return str.replace(/(^\s*)|(\s*$)/g, "");   
}  

