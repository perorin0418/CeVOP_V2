#include "stdafx.h"
#include <ObjBase.h>
#include <string>
#include <iostream>
using namespace std;

// タイプライブラリインポート
// （タイプライブラリの登録は、【CeVIO Creative Studio】インストール時に行われます。）
#import "libid:D3AEA482-B527-4818-8CEA-810AFFCB24B6" named_guids rename_namespace("CeVIO")

int main(int argc, char *argv[])
{
	string cast        = "タカハシ";
	string param1_name = "元気";
	string param2_name = "普通";
	string param3_name = "へこみ";
	string param4_name = "";
	
	int volume       = 0;
	int speed        = 0;
	int tone         = 0;
	int alpha        = 0;
	int toneScale    = 0;
	int param1       = 0;
	int param2       = 0;
	int param3       = 0;
	int param4       = 0;
	string text      = "";
	int isSave       = 0;
	string savePath  = "";

	// 引数が誤っている場合は終了
	if (argc != 13) {
		cout << "引数が足りません : "<< argc << endl;
		return -1;
	}
	
	// コマンドライン引数取得
	string _volume    = argv[1];
	string _speed     = argv[2];
	string _tone      = argv[3];
	string _alpha     = argv[4];
	string _toneScale = argv[5];
	string _param1    = argv[6];
	string _param2    = argv[7];
	string _param3    = argv[8];
	string _param4    = argv[9];
	string _text      = argv[10];
	string _isSave    = argv[11];
	string _savePath  = argv[12];

	volume = stoi(_volume);
	speed = stoi(_speed);
	tone = stoi(_tone);
	alpha = stoi(_alpha);
	toneScale = stoi(_toneScale);
	param1 = stoi(_param1);
	param2 = stoi(_param2);
	param3 = stoi(_param3);
	param4 = stoi(_param4);
	text = _text;
	isSave = stoi(_isSave);
	savePath = _savePath;

	cout << "volume => " << volume << endl;
	cout << "speed => " << speed << endl;
	cout << "tone => " << tone << endl;
	cout << "alpha => " << alpha << endl;
	cout << "toneScale => " << toneScale << endl;
	cout << "param1 => " << param1 << endl;
	cout << "param2 => " << param2 << endl;
	cout << "param3 => " << param3 << endl;
	cout << "param4 => " << param4 << endl;
	cout << "text => " << text << endl;
	cout << "isSave => " << isSave << endl;
	cout << "savePath => " << savePath << endl;

	// COM初期化
	::CoInitialize(NULL);

	// ServiceControlインスタンス生成
	CeVIO::IServiceControl *pServiceControl;
	HRESULT result0 = ::CoCreateInstance(CeVIO::CLSID_ServiceControl,
		NULL,
		CLSCTX_INPROC_SERVER,
		CeVIO::IID_IServiceControl,
		reinterpret_cast<LPVOID *>(&pServiceControl));
	if (FAILED(result0)) {
		// 失敗
		::CoUninitialize();
		return -1;
	}

	// ServiceControlのバージョン4.0追加機能インターフェイスを取得
	CeVIO::IServiceControlV40Part *pServiceControlV40Part = NULL;
	HRESULT result = pServiceControl->QueryInterface(CeVIO::IID_IServiceControlV40Part,
		reinterpret_cast<LPVOID *>(&pServiceControlV40Part));

	// 【CeVIO Creative Studio】起動
	pServiceControl->StartHost(false);

	// Talkerインスタンス生成
	CeVIO::ITalker *pTalker;
	HRESULT result1 = ::CoCreateInstance(CeVIO::CLSID_Talker,
		NULL,
		CLSCTX_INPROC_SERVER,
		CeVIO::IID_ITalker,
		reinterpret_cast<LPVOID *>(&pTalker));
	if (FAILED(result1)) {
		// 失敗
		::CoUninitialize();
		return -1;
	}

	// キャスト設定
	pTalker->Cast = cast.c_str();

	// 音量設定
	pTalker->Volume = volume;

	// 話速設定
	pTalker->Speed = speed;

	// 高さ設定
	pTalker->Tone = tone;

	// 声質設定
	pTalker->Alpha = alpha;

	if (param1 != -1) {
		// パラメーター1設定
		pTalker->Components->ByName(param1_name.c_str())->Value = param1;
	}

	if (param2 != -1) {
		// パラメーター2設定
		pTalker->Components->ByName(param2_name.c_str())->Value = param2;
	}

	if (param3 != -1) {
		// パラメーター3設定
		pTalker->Components->ByName(param3_name.c_str())->Value = param3;
	}

	if (param4 != -1) {
		// パラメーター4設定
		pTalker->Components->ByName(param4_name.c_str())->Value = param4;
	}

	// ServiceControlのバージョン4.0追加機能インターフェイスを取得
	CeVIO::ITalkerV40Part *pTalkerV40Part = NULL;
	result = pTalker->QueryInterface(CeVIO::IID_ITalkerV40Part,
		reinterpret_cast<LPVOID *>(&pTalkerV40Part));
	if (SUCCEEDED(result)) {
		// （例）抑揚設定
		pTalkerV40Part->ToneScale = toneScale;
	}

	if (isSave == 1) {
		boolean ret = pTalker->OutputWaveToFile(text.c_str(), savePath.c_str());
		if (!ret) {
			::CoUninitialize();
			return -1;
		}
	}else{
		// 再生
		CeVIO::ISpeakingStatePtr pState = pTalker->Speak(text.c_str());
		pState->Wait();
	}

	// Talker解放（追加部分）
	if (pTalkerV40Part != NULL) pTalkerV40Part->Release();

	// Talker解放
	pTalker->Release();

	// 【CeVIO Creative Studio】終了
	//pServiceControl->CloseHost(0);

	// ServiceControl解放（追加部分）
	if (pServiceControlV40Part != NULL) pServiceControlV40Part->Release();

	// ServiceControl解放
	pServiceControl->Release();

	// COM使用終了
	::CoUninitialize();

	return 0;
}