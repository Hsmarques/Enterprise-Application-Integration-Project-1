<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		
		<html>
  <body>
  <h2>PhoneList</h2>
    <table border="1">
      <tr bgcolor="#9acd32">
        <th style="text-align:left">Model</th>
        <th style="text-align:left">URL</th>
        <th style="text-align:left">Price</th>
        <th style="text-align:left">Processor</th>
        <th style="text-align:left">Screen Type</th>
        <th style="text-align:left">Screen Size</th>
        <th style="text-align:left">Frequency</th>
        <th style="text-align:left">Network</th>
      </tr>
      <xsl:for-each select="//smartphone">
				<tr>
						<td>
							<xsl:value-of select="title" />
						</td>
						<td>
							<xsl:value-of select="price" />
						</td>
						<td>
							<xsl:value-of select="url" />
						</td>
						<td>
							<xsl:value-of select="processor" />
						</td>
						<td>
							<xsl:value-of select="os" />
						</td>
						<td>
							<xsl:value-of select="screen/technology" />
						</td>
						<td>
							<xsl:value-of select="screen/size" />
						</td>
				</tr>
			</xsl:for-each>
    </table>
  </body>
  </html>
		
		
	</xsl:template>
</xsl:stylesheet>