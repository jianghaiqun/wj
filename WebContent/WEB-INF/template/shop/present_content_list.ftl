<li>
  <a href=${present.htmlFilePath} target="_top">
    <img src=../${present.imageaddress} height="90" width="120"/>
  </a>
    <br /><#if (present.name?length > 50)>${present.name[0..46]}...<#else>${present.name}</#if>
    &nbsp;&nbsp;&nbsp;<a class="fixed_width" href=${present.htmlFilePath} target="_top">兑换</a>
 </li>