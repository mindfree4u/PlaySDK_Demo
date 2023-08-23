package com.company.PlaySDK;

public class IPlaySDKCallBack
{

	public static class FRAME_INFO
	{
		public int			nWidth;					//Width, unit is pixel, 0 for audio data.
		public int			nHeight;				//height, 0 for audio data
		public int			nStamp;					//Time stamp info, unit is ms
		public int			nType;					//Video frame type,T_AUDIO16,T_RGB32,T_IYUV
		public int			nFrameRate;				//Image frame rate created during encoding
	};

	public static class FRAME_INFO_EX
	{
		public int				nFrameType;				//Frame Type
		public int				nFrameSeq;				//Frame serial number
		public int				nStamp;					//Frame time ,MilliSecond
		public int				nWidth;					//Width, unit is pixel, 0 for audio data.
		public int 				nHeight;				//height, 0 for audio data.
		public int				nFrameRate;				//Image frame rate created during encoding
		public int				nChannels;				//Audio Channel No.
		public int				nBitPerSample;			//Audio Sampling Bit
		public int				nSamplesPerSec;			//Audio Sampling Frequency
		public int				nYear;					//year
		public int				nMonth;					//month
		public int				nDay;					//day
		public int				nHour;					//hour
		public int				nMinute;				//minute
		public int				nSecond;				//second
		public int				nReserved[] = new int[64]; //Reserved
	};

	public static class FRAME_DECODE_INFO
	{
		public int				nFrameType;				//Frame type, definition as FRAME_INFO_EX nFrameType text
		public byte[]			pAudioData;          	//Audio data if it is audio frame
		public int				nAudioDataLen;			//Audio Data Length
		
		public byte[]			pVideoDataY;			//as YUV components
		public byte[]			pVideoDataU;
		public byte[]			pVideoDataV;
		public int				nStride[] = new int[3];		  //as spacing among YUV components
		public int				nWidth[]  = new int[3];	      //as width of YUV components
		public int				nHeight[] = new int[3];	      //as height of YUV components
		public int				nReserved[] = new int[64]; 	  //Reserved
	};

	public static class DEMUX_INFO
	{
		public int type;			// 1:VIDEO, 2:AUDIO, 3:DATA
		public int subtype;		    // I Frame, BP Frame, PCM8, MS-ADPCM etc.
		public int encode;			// MPEG4, H264, STDH264
		public int sequence;		// Frame sequence

		public int width;			// Width, unit is pixel, 0 for audio data.
		public int height;			// height, unit is pixel, 0 for audio data
		public int rate;			// Image frame rate created during encoding
	
		public int year;			// time info
		public int month;
		public int day;
		public int hour;
		public int minute;
		public int secode;
		public int timestamp;       // Time stamp info, unit is ms
		
		public int channels;		// Audio Channel No.
		public int bitspersample;	// Audio Sampling Bit
		public int samplespersecond;// Audio Sampling Frequency
	};


	/************************************************************************/
	/* call back interface	                                                   */
	/************************************************************************/
	/** 
	 * Description: audio video(with stride) decode or render data call back, used by PLAYSetDecodeCallBack, PLAYSetVisibleDecodeCallBack.
	 * @param nPort port number.
	 * @param pFrameDecodeInfo class defined as followings:\n
	 *		  public int			nFrameType;	    Frame type, definition as FRAME_INFO_EX nFrameType text. \n
	 *		  public byte[]			pAudioData;     Audio data if it is audio frame. \n
	 *	      public int			nAudioDataLen;  Audio Data Length. \n
	 *	      public byte[]			pVideoDataY;	Y components. \n
	 *	      public byte[]			pVideoDataU;    U components. \n
	 *	      public byte[]			pVideoDataV;    V components. \n
	 *	      public int			nStride[] = new int[3];	  as spacing among YUV components. \n
	 *	      public int			nWidth[]  = new int[3];	  as width of YUV components. \n
	 *	      public int			nHeight[] = new int[3];	  as height of YUV components. \n
	 *	      public int			nReserved[] = new int[64];Reserved.\n
	 * @param pFrameInfo class defined as followings: \n
	 * 		  public int				nFrameType;		Frame Type. \n
	 *		  public int				nFrameSeq;		Frame serial number. \n
	 *		  public int				nStamp;			Frame time ,MilliSecond. \n
	 *		  public int				nWidth;			Width, unit is pixel, 0 for audio data.\n
	 *		  public int 				nHeight;		height, 0 for audio data.\n
	 *		  public int				nFrameRate;		Image frame rate created during encoding.\n
	 *		  public int				nChannels;		Audio Channel No.\n
	 *		  public int				nBitPerSample;	Audio Sampling Bit.\n
	 *		  public int				nSamplesPerSec;	Audio Sampling Frequency.\n
	 *		  public int				nYear;			time info. \n
	 *		  public int				nMonth;			\n
	 *		  public int				nDay;			\n
	 *		  public int				nHour;			\n
	 *		  public int				nMinute;		\n
	 *		  public int				nSecond;		\n
	 * 		  public int				nReserved[] = new int[64]; Reserved
	 * @param pUserData Reserved.
	 */
	public interface fCBDecode {
		public void invoke(int nPort, FRAME_DECODE_INFO pFrameDecodeInfo, FRAME_INFO_EX pFrameInfo,	long pUserData);
	}

	/** 
	 * Description: video snap call back,used by PLAYSetDisplayCallBack.
	 * @param nPort port number.
	 * @param pBuf buffer pointer.
	 * @param nSize buffer len.
	 * @param nWidth Picture nWidth.
	 * @param nHeight Picture nHeight.
	 * @param nStamp Picture TimeStamp.
	 * @param nType data type,detail see Contstants. T_AUDIO16 T_RGB32 T_IYUV.
	 * @param pUserData Reserved.
	 */
	public interface fDisplayCBFun {
		public void invoke(int nPort,byte[] pBuf,int nSize,int nWidth,int nHeight,int nStamp,int nType, long pUserData);
	}

	/** 
	 * Description: get current surface device context, used by PLAYRigisterDrawFun.
	 * @param nPort port number.
	 * @param hDc hdc.
	 * @param pUserData Reserved.
	 */
	public interface fDrawCBFun {
		public void invoke(int nPort,int regionnum, long eglContext, long pUserData);
	}
	
	/** 
	 * Description: file end done call back, used by PLAYSetFileEndCallBack.
	 * @param nPort port number.
	 * @param pUserData Reserved.
	 */
	public interface fpEncChangeCBFunEx {
		public void invoke(int nPort, int width, int height, long pUserData);
	}
	
	/** 
	 * Description: Enc Change call back Ex, used by PLAYSetFileEndCallBackEx.
	 * @param nPort port number.
	 * @param pUserData Reserved.
	 */
	public interface fpFileEndCBFun {
		public void invoke(int nPort, long pUserData);
	}

	/** 
	 * Description: file reference done call back, used by PLAYSetFileRefCallBackEx.
	 * @param nPort port number.
	 * @param bIndexCreated 1 means create index success.
	 * @param pUserData Reserved.
	 */
	public interface fpFileRefDoneCBFunEx {
		public void invoke(int nPort, int bIndexCreated, long pUserData);
	}

	/** 
	 * Description: water mark call back, used by PLAYSetWaterMarkCallBackEx.
	 * @param nPort port number.
	 * @param buf buffer pointer.
	 * @param lTimeStamp Frame TimeStamp. 
	 * @param lInfoType
	 * @param len
	 * @param reallen
	 * @param lCheckResult
	 * @param pUserData Reserved.
	 */
	public interface GetWaterMarkInfoCallbackFuncEx {
		public void invoke(int nPort, byte[] buf, int lTimeStamp, int lInfoType, int len, int reallen, int lCheckResult, long pUserData);
	}

	/** 
	 * Description: audio video(no stride) render data call back, used by used by PLAYSetDecCallBackEx or PLAYSetVisibleDecCallBack.
	 * @param nPort port number.
	 * @param pBuf  buffer pointer.
	 * @param nSize buffer len.
	 * @param FrameInfo class defined as followings:\n
	 * 				public int			nWidth;	    Width, unit is pixel, 0 for audio data.\n
	 *				public int			nHeight;    height, 0 for audio data. \n
	 *				public int			nStamp;	    Time stamp info, unit is ms. \n
	 *				public int			nType;		Video frame type,T_AUDIO16,T_RGB32,T_IYUV. \n
	 *				public int			nFrameRate;	Image frame rate created during encoding 
	 * @param pUserData Reserved.
	 */
	public interface fVisibleDecCBFun {
		public void invoke(int nPort,byte[] pBuf,int nSize,FRAME_INFO FrameInfo, long pUserData);
	}

	/** 
	 * Description: audio record call back, used by PLAYOpenAudioRecord.
	 * @param pDataBuffer buffer pointer.
	 * @param nBufferLen  buffer len.
	 * @param pUserData Reserved.
	 */
	public interface pCallFunction {
		public void invoke(byte[] pDataBuffer,int nBufferLen, long pUserData);
	}

	/** 
	 * Description: source data call back, used by PLAYSetDemuxCallBack.
	 * @param nPort port number.
	 * @param pFrameData frame pointer, incluing head.
	 * @param datalen    frame len.
	 * @param pFrameBodyData stream pointer.
	 * @param bodylen		 stream len.
	 * @param DemuxInfo class defined as followings:\n
	 *					public int type;			1:VIDEO, 2:AUDIO, 3:DATA \n
	 *					public int subtype;		    I Frame, BP Frame, PCM8, MS-ADPCM etc.\n
	 *					public int encode;			MPEG4, H264, STDH264. \n
	 *					public int sequence;		Frame sequence. \n
	 *					public int width;			Width, unit is pixel, 0 for audio data.\n
	 *					public int height;			height, unit is pixel, 0 for audio data. \n
     *					public int rate;			Image frame rate created during encoding. \n					
	 * 					public int year;			time info. \n
	 *					public int month; \n
	 *					public int day;   \n
 	 *					public int hour;  \n
	 *					public int minute;\n
	 *					public int secode;\n  
	 *					public int timestamp;       Time stamp info, unit is ms. \n		
	 *					public int channels;		Audio Channel No.\n
	 *					public int bitspersample;	Audio Sampling Bit.\n
	 *					public int samplespersecond;Audio Sampling Frequency.
	 * @param pUserData Reserved.
	 */
	public interface fDemuxCBFun {
		public void invoke(int nPort, byte[] pFrameData, int datalen, byte[] pFrameBodyData, int bodylen, DEMUX_INFO DemuxInfo, long pUserData);
	}

	/** 
	 * Description: local file starttime, endtime call back, used by PLAYSetFileTimeDoneCallBack.
	 * @param nPort port number.
	 * @param nStarTime file starttime.
	 * @param nEndTime  file endtime.
	 * @param pUserData Reserved.
	 */
	public interface fFileTimeDoneCBFun {
		public void invoke(int nPort, int nStarTime, int nEndTime, long pUserData);
	}
	
	/** 
	 * Description: Record error call back, used by PLAYStartDataRecord.
	 * @param nPort port number.
	 * @param pUserData Reserved.
	 */
	public interface fRecordErrorCallBack {
		public void invoke(int nPort, long pUserData);
	}

}
