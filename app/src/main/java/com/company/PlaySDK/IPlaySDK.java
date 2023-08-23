package com.company.PlaySDK;

import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

import com.company.PlaySDK.IPlaySDKCallBack.*;

public class IPlaySDK
{
	static {		
		String []liblist = 
			{
				"gnustl_shared",
				"play",
				"jniplay",
				"hwdec"
				};
		
		for(String lib : liblist)
		{
			try
			{
				System.loadLibrary(lib);
			}
			catch(UnsatisfiedLinkError ulink)
			{
				Log.d("playsdk_log","Can not load decode library" + lib);
				ulink.printStackTrace();
			}
		}
		
	}
	
	public static class CUSTOMRECT
	{
		public int left;
		public int top;
		public int right;
		public int bottom;
	};
	
	public static class MEDIA_INFO
	{
		public int			lWidth;					
		public int			lHeight;				
		public int			lFrameRate;				
		public int			lChannel;				
		public int			lBitPerSample;			
		public int			lSamplesPerSec;			
	};

	
	/**
	 * Description: Open file interface.
	 * @param nPort Port number
	 * @param sFileName File name, The file size ranges from 4G to 4K.
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYOpenFile(int nPort,String sFileName);
	
	/**
	 * Description: Close played file, call after PLAYStop.
	 * @param nPort Port number
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYCloseFile(int nPort);
	
	/**
	 * Description: Open stream interface (Just the same as open file)
	 * @param port Port number
	 * @param pFileHeadBuf It is not avialbale now. Please set null.
	 * @param nSize	It is not avialbale now. Please set 0.
	 * @param nBufPoolSize Set storage data stream buffer size.The value ranges is [SOURCE_BUF_MIN,SOURCE_BUF_MAX]. Usually it is 900*1024. 
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYOpenStream(int port,  byte[] pFileHeadBuf, int nSize, int nBufPoolSize);
	
	/** Description: close data stream (just the same as close file), call after PLAY_Stop.
	 *  @param port Port number
	 *  @return true succeeded,false failed.
	 */
	public native static int PLAYCloseStream(int port);

	/**
	 * Description: Internal Function, the user need not be concerned.
	 * @param port Port number
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYPlay(int port, Surface surface);

	
	/**
	 * Description: Internal Function, the user need not be concerned.
	 * @param port Port number
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYStop(int port);

	/**
	 * Description: Input the stream data you get from the card. Enable stream and Then call PLAY_Play to input data.
	 * @param port Port number
	 * @param buffer Buffer address
	 * @param nSize Buffer size
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYInputData(int port, byte[] buffer, int nSize);
	
	/**
	 * Description: Pause/resume
	 * @param nPort Port number
	 * @param nPause TRUE Pause,FLASE Resume
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYPause(int nPort,short nPause);
	
	/**
	 * Description: Fast play. The speed escalates one level and support max 4 times in one callback.You can call PLAY_Play to resume normal playback from current position.
	 * @param nPort Port number
	 * @return true succeeded,false failed.
	 */
	public native static int  PLAYFast(int nPort);


	/**
	 * Description: Slow play. The same as the above. Each time the speed lowers one level. 
	 *				Support max callback 4 times. Pleae call PLAY_Play to resume normal play.
	 * @param nPort Port number
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSlow(int nPort);
	
	/**
	 * Description: Play frame by frame. Please call PLAY_ Play to resume normal playback.
	 * @param nPort Port number
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYOneByOne(int nPort);

	/**
	 * Description: Set relative position of file play pointer (unit :%).
	 * @param nPort Port number
	 * @param fRelativePos The value ranges is [0, 100%]
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetPlayPos(int nPort,float fRelativePos);
	
	/**
	 * Description: Set play direction.
	 * @param nPort Port number
	 * @param emDirection Play direction:0,Forward ,1,BackWard
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetPlayDirection(int nPort, short emDirection);

	/**
	 * Description: Get the relative position of the file player pointer.
	 * @param nPort Port number
	 * @return float,The value ranges is [0, 100%]
	 */
	public native static float PLAYGetPlayPos(int nPort);


	/**
	 * Description: Set volume.
	 * @param nPort Port number
	 * @param nVolume Volume value. The value ranges is [0, 0XFFFF].
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetVolume(int nPort,int nVolume);

	/**
	* Description: Get volume.
	* @param nPort Port number
	* @return Volume value.
	*/
	public native static int PLAYGetVolume(int nPort);

	/**
	 * Description: Disable audio.
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYStopSound();

	/**
	 * Description: Enable audio. There is only 1-ch audio in one time. System auto 
	 * 				disables previous audio if current audio is enabled. 
	 * 				Please note audio is disabled by default.
	 * @param nPort Port number
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYPlaySound(int nPort);
	
	/**
	 * Description: Get file total time length. Unit is second.
	 * @param nPort Port number
	 * @return int, total file time
	 */
	public native static int PLAYGetFileTime(int nPort);

	/**
	 * Description: Get file played time. Unit is second.
	 * @param nPort Port number
	 * @return int, The file current played time
	 */
	public native static int PLAYGetPlayedTime(int nPort);

	/**
	 * Description: Get the video frame amount that has been decoded.
	 * @param nPort Port number
	 * @return int, The video frame amount that have been decoded.
	 */
	public native static int PLAYGetPlayedFrames(int nPort);

	
	/**
	 * Description: Set callback function. It is to replace the displayed section in the player. You can control the displayed video.
	 *              Please call this function before PLAY_Play. This function becomes null when calling PLAY_Stop. 
	 *				You need to reset when you call PLAY_Play the next time.The decode section does not control speed. 
	 *				When you return from the callback function, the decoder will decode the next data. Please note before using this function; 
	 *				you need to fully understand the video display and audio play. Otherwise, do not use this function casually!
	 * @param nPort Port number
	 * @param cbDec see IPlaySDKCallBack,Callback function pointer. It can not be null,its parameter definitions are as following:
	 *				nPort,Port number
	 *				pFrameDecodeInfo,Audio and video data after decoding
	 *				pFrameInfo,Image and audio information. Please refer to the following information
	 *				pUserData,Reserved
	 * @param  pUser not used
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetDecodeCallBack(int nPort, fCBDecode cbDec, long pUser);
	
	/**
	 * 	Description: Set video data callback. It can be used to snapshoot. You can set callback function
	 *				 pointer of DisplayCBFun as null to stop callback. The callback function is valid all the time 
	 *			     once it is set, until the program exits. You can call this function at any time.
	 * @param nPort Port number
	 * @param DisplayCBFun see IPlaySDKCallBack, Video data callback function. It can be null,its parameter definitions are as following:
	 *					   nPort,Port number
	 *				       pBuf,Video data buffer
	 *				       nSize,video data size
	 *				       nWidth,Image width. Unit is pixel
	 *				       nHeight,Image height
	 *				       nStamp,Time mark information. Unit is ms
	 *				       nType,Data type. T_RGB32, T_UYVY. Please refer to macro definition
	 *				       pUserData,Reserved
	 * @param pUserData not used
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetDisplayCallBack(int nPort, fDisplayCBFun DisplayCBFun, long pUserData);
	
	/**
	 * 	Description: Save the snapshoot image as BMP file.This switch will occupy CPU resources. Do not call this 
	 *				 function if you do not need to save image.
	 * @param pBuf Image data buffer
	 * @param nSize Image data size
	 * @param nWidth Image width. Unit is pixel.
	 * @param nHeight,Image height. Unit is pixel.
	 * @param nType Data type. T_RGB32, T_UYVY. Please refer to macro definition.
	 * @param sFileName File name. The file extension name is BMP.
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYConvertToBmpFile(byte[] pBuf,int nSize,int nWidth,int nHeight,int nType, String sFileName);
	
	/**
	 * Description: Convert YUV image data to jpeg format.
	 * @param pYUVBuf Image data buffer
	 * @param nWidth Image width
	 * @param nHeight Image height
	 * @param YUVtype YUV data type. T_YV12,T_UYVY, ex.
	 * @param quality Compress quality, (0, 100]
	 * @param sFileName File name. The file extension name is jpg.
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYConvertToJpegFile(byte[] pYUVBuf, int nWidth, int nHeight, int YUVtype, int quality, String sFileName);


	/**
	 * Description: Get total frame count.
	 * @param nPort Port number
	 * @return int,Total frame count
	 */
	public native static int PLAYGetFileTotalFrames(int nPort);
	
	/**
	 * Description: get encode frame rate in current bit stream.
	 * @param nPort Port number
	 * @return int,Frame rate value when encoding current bit stream.
	 */
	public native static int PLAYGetCurrentFrameRate(int nPort);
	
	/**
	 * Description: Get file played time. Unit is ms
	 * @param nPort Port number
	 * @return int,The file current played time
	 */
	public native static int PLAYGetPlayedTimeEx(int nPort);
	
	/**
	 * Description: Set file play position according to the time. This interface takes a little bit longer 
	 *				than the PLAY_SetPlayPos. But use time to control progress bar (working with PLAY_GetPlayedTime(Ex))
	 *				to make the progress bar smoothly.
	 * @param nPort Port number
	 * @param nTime Set the file play postion to the specified time. Unit is ms
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetPlayedTimeEx(int nPort,int nTime);
		
	/**
	 *  Description: Get current play frame serial number. PLAY_GetPlayedFrames is the total frame amount. If 
	 *			    the play position is the same, the return values of these two functions shall be very close, 
	 *			    unless data loss occurs.
	 * @param nPort Port number
	 * @return int,Current play frame number.
	 */
	public native static int PLAYGetCurrentFrameNum(int nPort);

	/**
	 * Description: Set stream play mode. You need to set before playing
	 * @param port Port number
	 * @param mode STREAME_REALTIME Real-time mode (default);STREAME_FILE File mode.Real-time mode. It is suitable for network real-time mode.The
	 *			   deocder card will begin decoding right now
	 *			   File mode. It is suitable for you to input file data as stream
	 *			   when PLAY_InputData() return FALSE, you need to wait and then input again.
	 */
	public native static void PLAYSetStreamOpenMode(int port, int mode);
	
	/**
	 * Description: Get player SDK main version, sub-version and pack No.
	 * @return The higher 16-bit represents current main version.9-16 represents sub-version. 1-8-bit represents sub-pack number.
	 *		   For example:return value is 0x00030107 then you can see: main version is 3, sub-version is 1 and pack number is 7.
	 */
	public native static int PLAYGetSdkVersion();
	
	/**
	 * Description: Get current error code. You can call this function to get error code if you fail to call one function.
	 * @param nPort Port number
	 * @return int,Refer to Error type
	 */
	public native static int PLAYGetLastError(int nPort);

	/**
	 * Description: Refresh to display. If you refresh when player is in pause mode, the window video 
	 *			    disappears. You can call this interface to get the video agaian. It is valid in pause and frame 
	 *			    by frame playback mode. System will return directly in other situations.
	 * @param nPort Port number
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYRefreshPlay(int nPort);

	/**
	 *  Description: Get the original image size from the bit stream and then adjust display window accordingly. 
	 *			     It does not need display card to support zoom function. It is very useful for those display cards 
	 *			     that does not support hardware zoom.
	 * @param nPort Port number
	 * @param pWidth  Width of the original image. In PAL format CIF resolution it is 352
	 * @param pHeight Height of the original image. In PAL format CIF resolution, the value is 288
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYGetPictureSize(int nPort,int[] pWidth, int[] pHeight);
	
	/**
	 * Description: Set video quality. When it is in high quality the video is vivid, but the CPU comsuption 
	 *			    is high. When system is in multiple-channel play mode, you can set a little bit lower quality so
	 *			    as to lower CPU comsuption. When you want to see one window in large zone, you can set it to high
	 *			    quality to get excellent video effect.
	 * @param nPort Port number
	 * @param bHighQuality The video quality is the best when it is 1.The video quality is the lowest when it is 0(default value.)
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetPicQuality(int nPort,int bHighQuality);

	/**
	 * Description: Play audio in share way. Play current channel audio while not disabling audio from other channels.
	 * @param nPort Port number
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYPlaySoundShare(int nPort);

	/**
	 * Description: Disable audio in share way. PLAY_PlaySound and PLAY_StopSound are enable audio in 
	 *			    exclusive way. Please note, in one progress, all channels are required to use
	 *			    same way to enable or disable audio.
	 * @param nPort Port number
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYStopSoundShare(int nPort);

	/**
	 * Description: Get stream mode type.
	 * @param nPort Port number
	 * @return int,STREAME_REALTIME or STREAME_FILE.
	 */
	public native static int PLAYGetStreamOpenMode(int nPort);
	
	/**
	 * Description: Clear the remained data in sourcing buffer in stream play mode.
	 * @param nPort Port number
	 * @return int, true--succeeded false--failed.
	 */
	public native static int PLAYResetSourceBuffer(int nPort);
	
	/**
	 * Description: Playback frame by frame. Each callback will return one frame.
	 *			    You can call this function after creating the file index
	 * @param nPort Port number
	 * @return int, true--succeeded false--failed.
	 */
	public native static int PLAYOneByOneBack( int nPort);
	
	/**
	 * Description: Specify the frame number in current position. Locate the play position according to the 
	 *				frame number. The function need to be called back after creating the file index.
	 * @param nPort Port number
	 * @param nFrameNum Frame number
	 * @return int, true--succeeded false--failed.
	 */
	public native static int PLAYSetCurrentFrameNum( int nPort,int nFrameNum);

	/**
	 * Description: Set callback stream type when decoding.
	 * @param nPort Port number
	 * @param nStream 1 Video stream;2 Audio stream;3 Composite stream.
	 * @return int, true--succeeded false--failed.
	 */
	public native static int PLAYSetDecCBStream( int nPort,int nStream);
	
	/**
	 * Description: Set or add displayed zone. Support partial enlargement.
	 * @param nPort Port number
	 * @param nRegionNum Display zone serial number.0~(MAX_DISPLAY_WND-1).If nRegionNum is 0, it means the main display window.
	 * @param pSrcRect Partial displayed zone
	 * @param hDestWnd Display window handle
	 * @param bEnable Open (set) or close displayed zone
	 * @return int, true--succeeded false--failed.
	 */
	public native static int PLAYSetDisplayRegion( int nPort,int nRegionNum, CUSTOMRECT pSrcRect, Surface surface, int bEnable);

	/**
	 * Description: Register one callback function to get current surface device context. You can draw 
	 *			(write) in the DC just as you do in client zone DC of the window. But this DC is not the client 
	 *			zone DC of the window, it is the DC of Off-Screen in DirectDraw. Please note: This interface is 
	 *			null if you use overlay surface. You can draw in the window. It will be displayed if it is not
	 *			a transparent color.
	 * @param nPort Port number
	 * @param DrawFun see IPlaySDKCallBack, Callback function handle
	 *			      nPort,Port number
	 *			      hDc,OffScreen, You can operate it just as operate the displayed window DC
	 *			      pUserData, user customized parameter
	 * @param pUserData not used.
	 * @return int, true--succeeded false--failed.
	 */
	public native static int PLAYRigisterDrawFun(int nPort, int regionnum, fDrawCBFun DrawCBFun, long pUserData);

	/**
	 * Description: Clear player buffer.
	 * @param nPort Port number
	 * @param nBufType Buffer type, Please refer to macro definition macro definition: BUF_VIDEO_SRC 1 BUF_AUDIO_SRC 2 BUF_VIDEO_RENDER 3 BUF_AUDIO_RENDER 4 
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYResetBuffer(int nPort,int nBufType);

	/**
	 * Description: To get player buffer size (frame amount or byte). You can use this interface to get the 
	 *				data in the buffer so that you can estimate the network delaying time.
	 * @param nPort Port number
	 * @param nBufType BUF_VIDEO_SRC 1 BUF_AUDIO_SRC 2 BUF_VIDEO_RENDER 3 BUF_AUDIO_RENDER 4 
	 * @return int,Return different buffer values in accordance with various parameters. Sourcing buffer 
	 *			   returns byte and the buffer return the frame amount after decoding
	 */
	public native static int PLAYGetBufferValue(int nPort,int nBufType);

	/**
	 * Description: Adjust WAVE to adjust volume. The difference between this function and the PLAY_SetVolume is: 
	 *				the function is to adjust audio data and only applies to current channel. 
	 *				But PLAY_SetVolume is to adjust audio card volume and applies to the whole system.
	 *				This function has not realized yet.
	 * @param nPort Port number
	 * @param nCoefficient Modified parameter.,The value ranges:[MIN_WAVE_COEF, MAX_WAVE_COEF]. 0 stands for no modification.
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYAdjustWaveAudio(int nPort,int nCoefficient);

	/**
	 * Description: Set video parameter. It becomes active once it is set.
	 * @param nPort Port number
	 * @param nRegionNum Display zone. Please refer to PLAY_SetDisplayRegion. Please set it as o if there is only one display zone (usually it is 0)
	 * @param nBrightness Brightness. Default value is 64. The value ranges from 0 to 128
	 * @param nContrast Contratsness. Default value is 64. The value ranges from 0 to 128
	 * @param nSaturation Saturation.Default value is 64. The value ranges from 0 to 128
	 * @param nHue Hue.Default value is 64. The value ranges from 0 to 128
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetColor(int nPort, int nRegionNum, int nBrightness, int nContrast, int nSaturation, int nHue);

	/**
	 * Description: Get the corresponding color value. The parameter is the same as PLAY_SetColor.
	 * @param nPort Port number
	 * @param nRegionNum Display zone. Please refer to PLAY_SetDisplayRegion. Please set it as o if there is only one display zone (usually it is 0)
	 * @param Brightness Brightness. Default value is 64. The value ranges from 0 to 128
	 * @param Contrast Contratsness. Default value is 64. The value ranges from 0 to 128
	 * @param Saturation Saturation.Default value is 64. The value ranges from 0 to 128
	 * @param Hue Hue.Default value is 64. The value ranges from 0 to 128
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYGetColor(int nPort, int nRegionNum, Integer Brightness, Integer Contrast, Integer Saturation, Integer Hue);

	/**
	 * Description: Snapshoot, write picture data to specified file. PLAY_SetDisplayCallBack Call this function
	 *			when decoding video data and then you can process the video data (such as snapshoot). Keep calling
	 *			this callback function if there is continue decoding data. But LAY_CatchPic snapshoot one image 
	 *			at one time and it can realize snapshoot in pause or frame by frame play mode. If you want to snapshoot
	 *			(one time for one image), please call PLAY_CatchPic. You can call PLAY_SetDisplayCallBack to get
	 *			video data in a period of time.
	 * @param nPort Port number
	 * @param sFileName File name
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYCatchPic(int nPort,String sFileName) ;

	/**
	 * Description: Snapshoot. Picture format could be specified as bmp or jpeg.
	 * @param nPort Port number
	 * @param sFileName File name
	 * @param ePicfomat Picture format type, definition see Contants.
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYCatchPicEx(int nPort,String sFileName,int ePicfomat);
	
	/**
	 * Description: Set the end of file callback.
	 * @param nPort Port number
	 * @param pFileEndCBFun Callback function pointer
	 * @param pUserData not used
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetFileEndCallBack(int nPort, fpFileEndCBFun pFileEndCBFun,long pUserData);

	public native static int PLAYSetEncTypeChangeCallBackEx(int nPort, fpEncChangeCBFunEx pEncChangeCBFunEx, long pUserData);
	/**
	 * Description: Begin record stream data. It applies to stream mode only. Call it after PLAY_Play.
	 * @param nPort Port number
	 * @param sFileName Record file name. If there is no exsiting folder in the name, then create a new folder
	 * @param idataType 0 Raw video stream;1 convert to avi;2 convert to asf
	 * @param fRecordListener record callback.
	 * @param pUserData not used. 
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYStartDataRecord(int nPort, String sFileName, int idataType, fRecordErrorCallBack fRecordListener, long pUserData);

	/**
	 * Description:  Save native stream.
	 * @param nPort Port number
	 * @param pBuf Stream Buffer
	 * @param nSize Buffer Size
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYWriteData(int nPort, byte[] pBuf,int nSize);

	/**
	 * Description: Stop recording stream data.
	 * @param nPort Port number
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYStopDataRecord(int nPort);

	/**
	 * Description: Change play speed.
	 * @param nPort Port Number
	 * @param fCoff Play Speed,Range[1/64~64.0],less than 1 play slowly greater than 1 play quickly.
	 *				when play speed is high enough ,it wil probally throw frame.
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetPlaySpeed(int nPort, float fCoff);
	
	/**
	 * Description: Information status search function. Now it can search current time and frame rate information.
	 * @param nPort Port number
	 * @param cmdType Specify status search type.
	 * 				  PLAY_CMD_GetTime			get time information.Unit is ms
	 *			 	  PLAY_CMD_GetFileRate		get frame rate information
	 *			 	  PLAY_CMD_GetMediaInfo		get media information, sturct is MEDIA_INFO
	 * @param buf  Information buffer
	 * @param buflen Buffer length
	 * @param returnlen Valid data length
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYQueryInfoTime(int nPort);
	
	/**
	 * Description: Query information.
	 * @param nPort Port number
	 * @param cmdType Specify status search type.
	 * 				  PLAY_CMD_GetTime			get time information.Unit is ms
	 *			 	  PLAY_CMD_GetFileRate		get frame rate information
	 *			 	  PLAY_CMD_GetMediaInfo		get media information, sturct is MEDIA_INFO
	 * @param buf  Information buffer
	 * @param buflen Buffer length
	 * @param returnlen Valid data length
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYQueryInfo(int nPort , int cmdType, byte[] buf, int buflen, Integer returnlen);

	/**
	 * Description: Set whether to enable file debugging for audio echo cancel. It is off by default and valid on Android platform.
	 * @param bWriteData True means open the write file, False means close the write file.
	 * @param sConfigFile Software audio echo cancel configuration file path for parameter debugging.
	 * @return true succeeded,false failed.
	 */
    public native static int PLAYSetAecDebug(int bWriteData, String sConfigFile);

	/**
	 * Description: Set hardware audio echo cancel enable. Android platform is available.
	 * @param bEnable  Whether hardware audio echo cancel is enabled or not. True means "on", FALSE means "off", default is "off".
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYAecHardwareEnable(int bEnable);
	
	/**
	 * Description: Enabel audio sampling operation.
	 * @param pProc see IPlaySDKCallBack, Audio sampling data callback pointer,its parameter definitions are as following:
	 * 			    pDataBuffer,Data callback pointer
	 *				DataLength,Callback data length
	 *				pUserData, user customized parameter
	 * @param nBitsPerSample Represent the bit for each sampling
	 * @param nSamplesPerSec Sampling rate
	 * @param nLength Data buffer length
	 * @param nReserved not used
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYOpenAudioRecord(pCallFunction pProc, int nBitsPerSample, int nSamplesPerSec, int nLength, long nReserved);
	
	/**
	 * Description: Disable audio sampling function.
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYCloseAudioRecord();
	
	/**
	 * Description: Open file and then automatically allocate channel number.
	 * @param  sFileName File name, The file size ranges from 4G to 4K
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYCreateFile(String sFileName);

	/**
	 * Description: Close file and release the automatically allocated channel number.
	 * @param  nPort Port number
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYDestroyFile(int nPort);

	/**
	 * Description: Open stream interface and then automatically allocate channel number.
	 * @param nBufPoolSize Set storage data stream buffer size. The value ranges is [SOURCE_BUF_MIN,SOURCE_BUF_MAX]. Usually it is 900*1024. 
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYCreateStream(int nBufPoolSize);
	
	/**
	 * Description: Close data bit and release the automatically allocated port number.
	 * @param nPort Port number 
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYDestroyStream(int nPort);

	/**
	 * Description: Get free port. 
	 * @return port num. -1 means invalid port.
	 */
	public native static int PLAYGetFreePort();
	
	/**
	 * Description: Release the port got with PLAY_GetFreePort.
	 * @param nPort Port number
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYReleasePort(int lPort);

	/**
	 * Description: Vertical synchronize method, support offscreen mode only
	 *			    Called after PLAY_Play. When displaying dynamic images, this method may be useful.
	 * @param nPort Port number
	 * @param bEnable TRUE Enable vertical synchronize;FALSE Diable vertical synchronize.
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYVerticalSyncEnable(int nPort, int bEnable);

	/**
	 * Description: Snapshoot. Return BMP picture data buffer.
	 * @param nPort Port number
	 * @param pBmpBuf Picture buffer. Allocate by user, suggest size:
	 *				sizeof(BITMAPFILEHEADER) +
	 *				sizeof(BITMAPINFOHEADER) + w * h * 4
	 *				where w is picture width, h is picture height
	 * @param dwBufSize Buffer size
	 * @param pBmpSize BMP picture data size
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYGetPicBMP(int nPort, byte[] pBmpBuf, int dwBufSize, Integer pBmpSize);

	/**
	 * Description: snapshot BMP image.
	 * @param nPort Port number
	 * @param pBmpBuf buffer address to store BMP data, allocated by user, must be greater than bmp image size,
	 *				  Recommended size :size of(BITMAPFILEHEADER)+sizeof(BITMAPINFOHEADER)+w*h*4,
	 *				  w and h are image width and height.
	 * @param dwBufSize buffer area size
	 * @param pBmpSize image size of actual bmp 
	 * @param nWidth designated bmp width
	 * @param nHeight designated bmp height
	 * @param nRgbType designated RGB format 0��RGB32;1��RGB24;
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYGetPicBMPEx(int nPort, byte[] pBmpBuf, int dwBufSize, Integer pBmpSize, int nWidth, int nHeight, int nRgbType);

	/**
	 * Description: Snapshoot. Return JPEG picture data buffer.
	 * @param nPort Port number
	 * @param pJpegBuf Picture buffer. Allocate by user, suggest size: w * h * 3/2 where w is picture width, h is picture height
	 * @param dwBufSize Buffer size
	 * @param pJpegSize JPEG picture data size
	 * @param quality Quality of the jpeg compression, value is (0, 100]
	 * @return true succeeded,false failed.
	 */ 
	public native static int PLAYGetPicJPEG(int nPort, byte[] pJpegBuf, int dwBufSize, Integer pJpegSize, int quality);

	/**
	 * Description: Decoding call, is same almost with PLAY_SetDecCallBackEx, except that decoding call can
	 *				display video simultaneously, long-time logic processing is not recommended in call function, in order to avoid delay in display.
	 * @param nPort Port number
	 * @param cbDec see IPlaySDKCallBack,call function indicator, cannot be null, its parameter definitions are as following:
	 *				nPort, Port number
	 *				pFrameDecodeInfo, A/V data after decoding, refer to FRAME_DECODE_INFO structure
	 *				pFrameInfo, image and audio, refer to FRAME_INFO_EX structure
	 *				pUserData, user customized parameter
	 * @param pUserData not used
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetVisibleDecodeCallBack(int nPort, fCBDecode cbDec, long pUser);

	/**
	 * Description: Snap. Can set format, height and width. 
	 * @param nPort Port number
	 * @param sFileName file name 
	 * @param lTargetWidth picture width
	 * @param lTargetHeight picture height
	 * @param ePicfomat Picture format type, definition see Contants.
	 * @return true succeeded,false failed.
	 */ 
	public native static int PLAYCatchResizePic(int nPort, String sFileName, int lTargetWidth, int lTargetHeight, int ePicfomat);

	/**
	 * Description: Get video real-time bit rate.  
	 * @param nPort Port number
	 * @param pBitRate output Parameter,Returns Video bit rate 
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYGetRealFrameBitRate(int nPort, Integer pBitRate);
	
	/**
	 * Description: Set callback function pointer. Callback when establishing callback. To locate quickly 
	 *			  and accurately, system generates a file index when opening a file. This period may take a little 
	 *			  bit long. Its speed is about 40M/s, mainly because reading data from the HDD is slow. The index 
	 *			  establishment is operated in the background. The functions that need to use this index have to 
	 *			  wait until this process ends, while the other interfaces may not be affected.
	 * @param nPort Port number
	 * @param pFileRefDoneEx see IPlaySDKCallBack, Callback function pointer,its parameter definitions are as following:
	 *						 nPort,Port number
	 *						 bIndexCreated,Index creation symbol.TRUE=Index creation succeeded. FALSE=Index creation failed.
	 *						 pUserData, user customized parameter
	 * @param pUserData not used
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetFileRefCallBackEx(int nPort, fpFileRefDoneCBFunEx pFileRefDoneCBFunEx, long pUserData);

	/**
	 * Description: Set watermark data callback.
	 * @param nPort Port number
	 * @param pFunc see IPlaySDKCallBack, Watermark information to get callback function,its parameter definitions are as following:
	 *			    nPort,Port number
	 *				buf,Watermark data buffer
	 *				lTimeStamp,Watermark time stamp 
	 *				lInfoType,Distinguish different watermarks,There are three types: WATERMARK_DATA_TEXT,
	 *					WATERMARK_DATA_JPEG_BMP,WATERMARK_DATA_FRAMEDATA
	 *			    len,Buffer length
	 *			    reallen,Bufffer actual length
	 *			    lCheckResult,1 no error;2 frame water mark;3 frame data error ;4 frame sequence error
	 *				pUserData, user customized parameter
	 * @param pUserData not used
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetWaterMarkCallBackEx(int nPort, GetWaterMarkInfoCallbackFuncEx pFunc, long pUserData);


	/**
	 * Description: Set audio record data zoom rate.
	 * @param pfRatio zoom rate. From 0 to 1=Zoom in audio.1=Original audio. Over 1=zoom out audio.
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetAudioRecScaling(float fRatio);

	/**
	 * Description: Get audio record data zoom rate.
	 * @param pfRatio zoom rate. From 0 to 1=Zoom in audio.1=Original audio. Over 1=zoom out audio.
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYGetAudioRecScaling(Double pfRatio);
	
	/**
	 * Description: Set realplay delay time.
	 * @param nPort Port number
	 * @param nDelay Delay time
	 * @param nThreshold Threshold time
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetDelayTime(int nPort, int nDelay, int nThreshold);
	
	/**
	 * Description: Set decoding call to replace display in player, controlled by user, the function at
	 *				call before PLAY_Play, when PLAY_Stop, it automatically become invalid. Next time before PLAY_Play,
	 *				Need to set again, decoding part do not control speed, user shall return from call function, decoder
	 *				decode next data, only for decoding with no display.
	 * @param nPort Port number
	 * @param DecCBFun see IPlaySDKCallBack, call function indicator, cannot be null, its parameter definitions are as following:
	 *				nPort, Port number
	 *				pBuf, A/V data after decoding
	 *				nSize, pBuf length of A/V data after decoding
	 *				FrameInfo, image and audio, refer to FRAME_INFO structure
	 *				pUserData, user customized parameter
	 * @param pUserData not used.
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetDecCallBackEx(int nPort, fVisibleDecCBFun DecCBFun, long pUserData);

	/**
	 * Description: Decoding call, is same almost with PLAY_SetDecCallBackEx, except that decoding call can
	 *				display video simultaneously, long-time logic processing is not recommended in call function, in order to avoid delay in display.
	 * @param nPort Port number
	 * @param DecCBFun see IPlaySDKCallBack, call function indicator, cannot be null, its parameter definitions are as following:
	 *				nPort, Port number
	 *				pBuf, A/V data after decoding
	 *				nSize, pBuf length of A/V data after decoding
	 *				FrameInfo, image and audio, refer to FRAME_INFO structure
	 *				pUserData, user customized parameter
	 * @param pUserData not used.
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetVisibleDecCallBack(int nPort, fVisibleDecCBFun DecCBFun, long pUserData);

	/**
	* Description: scale picture
	* @param nPort Port number
	* @param scale scale rate. range[1.0, 8.0] is suggested, normal is 1.0.
	* @param nRegionNum Display zone serial number.0~(MAX_DISPLAY_WND-1).If nRegionNum is 0, it means the main display window.
	* @return true succeeded,false failed.
	*/
	public native static int PLAYScale(int nPort, float scale, int nRegionNum);

	/**
	* Description: translate picture, called after PLAYGetTranslateX and PLAYGetTranslateY.
	* @param nPort Port number
	* @param x absolute value of x coordinate
	* @param y absolute value of y coordinate
	* @param nRegionNum Display zone serial number.0~(MAX_DISPLAY_WND-1).If nRegionNum is 0, it means the main display window.
	* @return true succeeded,false failed.
	*/
	public native static int PLAYTranslate(int nPort, float x, float y, int nRegionNum);

	/**
	* Description: identity the matrix, reset translate and scale operation.
	* @param nPort Port number
	* @param nRegionNum Display zone serial number.0~(MAX_DISPLAY_WND-1).If nRegionNum is 0, it means the main display window.
	* @return true succeeded,false failed.
	*/
	public native static int PLAYSetIdentity(int nPort, int nRegionNum);

	/**
	* Description: get current scale rate.
	* @param nPort Port number
	* @param nRegionNum Display zone serial number.0~(MAX_DISPLAY_WND-1).If nRegionNum is 0, it means the main display window.
	* @return scale rate.
	*/
	public native static float PLAYGetScale(int nPort, int nRegionNum);
	
	public native static int PLAYGetSourceBufferRemain(int nPort);

	/**
	* Description: get current x coordinate of translate.
	* @param nPort Port number
	* @param nRegionNum Display zone serial number.0~(MAX_DISPLAY_WND-1).If nRegionNum is 0, it means the main display window.
	* @return x coordinate
	*/
	public native static float PLAYGetTranslateX(int nPort, int nRegionNum);

	/**
	* Description: get current y coordinate of translate.
	* @param nPort Port number
	* @param nRegionNum Display zone serial number.0~(MAX_DISPLAY_WND-1).If nRegionNum is 0, it means the main display window.
	* @return y coordinate
	*/
	public native static float PLAYGetTranslateY(int nPort, int nRegionNum);

	/**
	 * Description: set source data call back func.
	 * @param nPort Port number
	 * @param cbFun see IPlaySDKCallBack, call function indicator, cannot be null, its parameter definitions are as following:
	 *				nPort, Port number
	 *				pFrameData, frame data,including dahua head
	 *				datalen, frame data len
	 *				pFrameBodyData, body data,excluding dahua head
	 *				bodylen, body len	
	 *				DemuxInfo, data frame info, refer to DEMUX_INFO structure
	 *				pUserData, user customized parameter
	 * @param  pUserData not used.
	 * @return true succeeded,false failed.
	*/
	public native static int PLAYSetDemuxCallBack(int nPort, fDemuxCBFun cbFun, long pUserData);

	/**
	 * Description: set local file starttime and endtime call back func.
	 * @param nPort Port number
	 * @param FileTimeCBFun see IPlaySDKCallBack, call function indicator, cannot be null, its parameter definitions are as following:
	 *				nPort, Port number
	 *				nStarTime, local file starttime, total seconds from 1970/1/1.0.0.0.
	 *				nEndTime, local file endtime, total seconds from 1970/1/1.0.0.0.
	 *				pUserData, user customized parameter
	 * @param  pUserData not used.
	 * @return true succeeded,false failed.
	*/
	public native static int PLAYSetFileTimeDoneCallBack(int nPort, fFileTimeDoneCBFun FileTimeCBFun, long pUserData);

	/**
	 * Description: set play strategy��only for realtime
	 * @param nPort Port number
	 * @param nStartTime first start play if internal buffer is greater than nStartTime
	 * @param nSlowTime start play at lower speed if internal buffer is less than nSlowTime
	 * @param nFastTime start play at faster speed if internal buffer is greater than nFastTime
	 * @param nFailedTime InputData Interface will be failed if internal buffer is greater than nFailedTime
	 * @return true succeeded,false failed.
	*/
	public native static int PLAYSetPlayMethod(int nPort, int nStartTime, int nSlowTime, int nFastTime, int nFailedTime);

	/**
	 * Description: clean screen, call before PLAYStop.
	 * @param nPort Port number
	 * @param red rgba value.
	 * @param nRegionNum default 0.
	 * @return true succeeded,false failed.
	*/
	public native static int PLAYCleanScreen(int nPort, float red, float green, float blue, float alpha, int nRegionNum);


	/**
	 * Description: set aes encrypt key.
	 * @param nPort Port number
	 * @param szKey key(string).
	 * @param nKeylen the length of szKey.
	 * @return true succeeded,false failed.
	*/
	public native static int PLAYSetSecurityKey(int nPort, byte[] szKey, int nKeylen);
	
	/**
	 * Description: called before Play_Play
	 * @param nPort Port number
	 * @param nThreadNumber Thread Number (1 - 8)
	 * @return true succeeded,false failed.
	*/
	public native static int PLAYSetDecodeThreadNum(int nPort, int nThreadNumber);

	/**
	 * Description: use for real time stream, called before PLAYInputData
	 * @param nPort Port number
	 * @param nCacheMode Cache mode (0: off  1: adaptive  2:real time first 3: fluency first)
	 * @return true succeeded,false failed.
	 */
	public native static int PLAYSetCacheMode(int nPort, int nCacheMode);
	
	/**
	 * Description: called before Play_Play
	 * @param nPort Port number
	 * @param DecodeType
	 * @param RenderType
	 * @return true succeeded,false failed.
	*/
	public native static int PLAYSetEngine(int nPort, int DecodeType, int RenderType);
	
	/**
	 * Description: called before Play_Play
	 * @param nPort Port number
	 * @param nAVSyncType
	 * @return true succeeded,false failed.
	*/
	public native static int PLAYSetAVSyncType(int nPort, int nAVSyncType);
	
	/**
	 * Description: called before Play_Play
	 * @param nPort Port number
	 * @param rotateAngle 0: 0�� 1:90�� 2:180��  3:270��
	 * @return true succeeded,false failed.
	*/
	public native static int PLAYSetRotateAngle(int nPort, int rotateAngle);
	
	/**
	 * Description: print log switch
	 * @param enable 1 means switch on, 0 means switch off
	 * @return true succeeded,false failed.
	*/
	public native static void PLAYSetPrintLogSwitch(int enable);

    /**
     * Description: Intelligent Information switch
     * @param nPort Port number
     * @param enable 1 means switch on, 0 means switch off
     * @return true succeeded,false failed.
    */
    public native static void PLAYRenderPrivateData(int nPort, int enable);
    
    /**
     * Description: Flush last frame
     * @param nPort Port number
     * @return true succeeded,false failed.
    */
    public native static int PLAYFlush(int nPort);

	/************************************************************************/
	/* old java Interface implement                                         */
	/************************************************************************/
	public static int PLAYSurfaceChange(int port, SurfaceView surfaceView)
	{
		return PLAYSetDisplayRegion(port, 0, null, surfaceView != null ? surfaceView.getHolder().getSurface() : (Surface)null, 1);
	}

	public static int PLAYSetDisplayRegion( int nPort,int nRegionNum, CUSTOMRECT pSrcRect, SurfaceView surfaceView, int bEnable)
	{
		return PLAYSetDisplayRegion(nPort, nRegionNum, pSrcRect, surfaceView != null ? surfaceView.getHolder().getSurface() : (Surface)null, bEnable);
	}
	
	public static int PLAYPlay(int port, SurfaceView surfaceView)
	{
		return PLAYPlay(port, surfaceView != null ? surfaceView.getHolder().getSurface() : (Surface)null);
	}
	
	public static int InitSurface(int port, SurfaceView surfaceView)
	{
		return PLAYSetDisplayRegion(port, 0, null, surfaceView != null ? surfaceView.getHolder().getSurface() : (Surface)null, 1);
	}
	
	public static int UinitSurface(int port)
	{
		return PLAYSetDisplayRegion(port, 0, null, (Surface)null, 0);
	}
	
	public static int PLAYViewResolutionChanged(int nPort, int nWidth, int nHeight, int nRegionNum)
	{
		return 0;
	}
}
