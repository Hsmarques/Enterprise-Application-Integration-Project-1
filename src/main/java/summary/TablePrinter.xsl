<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		
		<html>
  <body>
  <h2>Pixmania Smartphone's</h2>
    <table border="1">
      <tr bgcolor="#546a75">
        <th style="text-align:center"><font color="white">Model</font></th>
        <th style="text-align:center"><font color="white">URL</font></th>
        <th style="text-align:center" width="4%"><font color="white">Price</font></th>
         <th style="text-align:center"><font color="white">OS</font></th>
        <th style="text-align:center"><font color="white">Processor</font></th>
        <th style="text-align:center"><font color="white">Screen Type</font></th>
        <th style="text-align:center"><font color="white">Screen Size</font></th>
        <th style="text-align:center"><font color="white">Frequency</font></th>
        <th style="text-align:center"><font color="white">Network</font></th>
        <th style="text-align:center"><font color="white">Bluetooth</font></th>
        <th style="text-align:center"><font color="white">Wi-fi</font></th>
        <th style="text-align:center"><font color="white">Camera</font></th>
        <th style="text-align:center"><font color="white">Batery Type</font></th>
        <th style="text-align:center"><font color="white">Autonomy</font></th>
        <th style="text-align:center"><font color="white">Dimensions</font></th>
        <th style="text-align:center"><font color="white">Weight</font></th>
      </tr>
      <xsl:for-each select="//smartphone">
				<tr>
						<td align = "center">
							<xsl:value-of select="model" />
						</td>
						<td  align = "justify">
							<xsl:value-of select="url" />
						</td>
						<td align = "center">
							<xsl:value-of select="price" />
						</td>
						<td>
							<xsl:value-of select="so" />
						</td>
						<td>
							<xsl:value-of select="processor" />
						</td>
						<td>
							<xsl:value-of select="screen/type" />
						</td>
						<td>
							<xsl:value-of select="screen/size" />
						</td>
						<td>
							<xsl:value-of select="frequency" />
						</td>
						<td>
							<xsl:value-of select="network" />
						</td>
						<td>
							<xsl:value-of select="communication/bluetooth" />
						</td>
						<td>
							<xsl:value-of select="communication/wifi" />
						</td>
						<td>
							<xsl:value-of select="camera" />
						</td>
						<td>
							<xsl:value-of select="battery_type" />
						</td>
						<td>
							<xsl:value-of select="autonomy" />
						</td>
						<td>
							<xsl:value-of select="dimensions" />
						</td>
						<td>
							<xsl:value-of select="weight" />
						</td>
						
				</tr>
			</xsl:for-each>
    </table>
  </body>
  </html>
		
		
	</xsl:template>
</xsl:stylesheet>