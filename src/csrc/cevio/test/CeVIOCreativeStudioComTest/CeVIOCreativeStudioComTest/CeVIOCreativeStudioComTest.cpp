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
		return 0;
	}

	// ServiceControlのバージョン4.0追加機能インターフェイスを取得
	CeVIO::IServiceControlV40Part *pServiceControlV40Part = NULL;
	HRESULT result = pServiceControl->QueryInterface(CeVIO::IID_IServiceControlV40Part,
		reinterpret_cast<LPVOID *>(&pServiceControlV40Part));
	if (SUCCEEDED(result)) {
		//MessageBox(NULL, pServiceControlV40Part->HostVersion.GetBSTR(), L"", MB_OK);
	}
	else {
		// バージョン4.0以前の場合はこちらに分岐
	}

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
		return 0;
	}

	int count;
	for (count = 0; count < pTalker->AvailableCasts->Length; count++) {
		cout << pTalker->AvailableCasts->At(count) << endl;
	}

	

	// キャスト設定
	pTalker->Cast = "さとうささら";

	// （例）音量設定
	pTalker->Volume = 100;

	// ServiceControlのバージョン4.0追加機能インターフェイスを取得
	CeVIO::ITalkerV40Part *pTalkerV40Part = NULL;
	result = pTalker->QueryInterface(CeVIO::IID_ITalkerV40Part,
		reinterpret_cast<LPVOID *>(&pTalkerV40Part));
	if (SUCCEEDED(result)) {
		// （例）抑揚設定
		pTalkerV40Part->ToneScale = 100;
	}

	// （例）再生
	//CeVIO::ISpeakingStatePtr pState = pTalker->Speak("こんにちは");
	//pState->Wait();

	// （例）音素データ取得
	CeVIO::IPhonemeDataArrayPtr pPhonemes = pTalker->GetPhonemes("はじめまして");

	// Talker解放（追加部分）
	if (pTalkerV40Part != NULL) pTalkerV40Part->Release();

	// Talker解放
	pTalker->Release();

	// 【CeVIO Creative Studio】終了
	pServiceControl->CloseHost(0);

	// ServiceControl解放（追加部分）
	if (pServiceControlV40Part != NULL) pServiceControlV40Part->Release();

	// ServiceControl解放
	pServiceControl->Release();

	// COM使用終了
	::CoUninitialize();

	return 0;
}