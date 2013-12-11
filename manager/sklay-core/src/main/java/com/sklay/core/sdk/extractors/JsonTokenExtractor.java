package com.sklay.core.sdk.extractors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sklay.core.sdk.exceptions.OAuthException;
import com.sklay.core.sdk.model.Token;
import com.sklay.core.sdk.utils.Preconditions;

public class JsonTokenExtractor implements AccessTokenExtractor
{
  private Pattern accessTokenPattern = Pattern.compile("\"access_token\":\"(\\S*?)\"");

  @Override
  public Token extract(String response)
  {
    Preconditions.checkEmptyString(response, "Cannot extract a token from a null or empty String");
    Matcher matcher = accessTokenPattern.matcher(response);
    if(matcher.find())
    {
      return new Token(matcher.group(1), "", response);
    }
    else
    {
      throw new OAuthException("Cannot extract an acces token. Response was: " + response);
    }
  }

}
