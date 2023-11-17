import React, { ChangeEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import * as W from "./WritingSpaces.Style";
import CreatingSpace from "./CreatingSpace/CreatingSpace";
import UpdatingSpace from "./updatingSpace/UpdatingSpace";
import { postFeed, updateFeed } from "../../../services/feedService";
import { postInformationType } from "../../../types/feedDataType";

interface WritingSpaceProps {
  page: string;
}

const WritingSpace: React.FC<WritingSpaceProps> = ({ page }) => {
  const [isFeedPublic, setFeedPublic] = useState<boolean>(true);
  const [isMapAssign, setMapAssign] = useState<boolean>(true);
  const [isSearched, setSearch] = useState<boolean>(false);
  const [location, setLocation] = useState<string>("");

  const [postInformation, setPostInformation] = useState<postInformationType>({
    title: "",
    content: "",
    state: isFeedPublic,
    map: isMapAssign,
    address: "",
  });

  const [updateInformation, setUpdateInformation] = useState<string>("");

  const navigator = useNavigate();

  const backToPrevPage = () => {
    navigator(-1);
  };

  const feedToggleCheck = () => {
    if (isFeedPublic === false) {
      setFeedPublic(true);
    } else {
      setFeedPublic(false);
    }
    handleInputChange("state", isFeedPublic);
  };

  const mapToggleCheck = () => {
    if (isMapAssign === false) {
      setMapAssign(true);
    } else {
      setMapAssign(false);
    }
    handleInputChange("map", isMapAssign);
  };

  const send = (e: React.MouseEvent<HTMLButtonElement>) => {
    const targetElement = e.target as HTMLElement;

    if (targetElement instanceof HTMLElement) {
      if (targetElement.textContent) {
        const textContent = targetElement.textContent;
        if (textContent === "게시") {
          postFeed(postInformation);
        } else if (textContent === "완료") {
          updateFeed(updateInformation);
        }
      }
    }
  };

  const handleInputChange = (fieldName: string, value: string | boolean) => {
    setPostInformation(() => ({
      ...postInformation,
      [fieldName]: value,
    }));
  };

  const handleContentChange = (content: string) => {
    setUpdateInformation(content);
  };

  const enrollLocation = (e: React.KeyboardEvent<HTMLInputElement>): void => {
    if (e.key === "Enter") {
      setSearch(true);
      handleInputChange("address", location);
    }
  };
  const deleteLocation = () => {
    if (isSearched === true) {
      setSearch(false);
    }
  };

  console.log(postInformation);
  console.log(updateInformation);

  return (
    <W.CreateFeedContainer>
      <W.FeedTopContainer>
        <W.BackspaceButton onClick={backToPrevPage} />
        <W.PageName>
          {page === "create" ? "새 피드 올리기" : "피드 수정"}
        </W.PageName>
        <W.CreateButton onClick={send}>
          {page === "create" ? "게시" : "완료"}
        </W.CreateButton>
      </W.FeedTopContainer>
      {page === "create" ? (
        <CreatingSpace handleInputChange={handleInputChange} />
      ) : (
        <UpdatingSpace handleContentChange={handleContentChange} />
      )}
      <W.FeedBottomContainer>
        <W.AddressContainer>
          {isSearched === false ? (
            <W.SearchLoaction
              onKeyDown={(e) => enrollLocation(e)}
              onChange={(e) => setLocation(e.target.value)}
            />
          ) : (
            <>
              <W.CancelBtn onClick={deleteLocation} />
              <W.SearchResult>{location}</W.SearchResult>
            </>
          )}
        </W.AddressContainer>
        <W.Toggles>
          <W.ToggleWrap onClick={() => feedToggleCheck()}>
            피드 숨기기
            <W.ToggleContainer data={isFeedPublic.toString()}>
              <W.ToggleCircle data={isFeedPublic.toString()} />
            </W.ToggleContainer>
          </W.ToggleWrap>
          <W.ToggleWrap onClick={() => mapToggleCheck()}>
            마이 펫 지도에서 숨기기
            <W.ToggleContainer data={isMapAssign.toString()}>
              <W.ToggleCircle data={isMapAssign.toString()} />
            </W.ToggleContainer>
          </W.ToggleWrap>
        </W.Toggles>
      </W.FeedBottomContainer>
    </W.CreateFeedContainer>
  );
};

export default WritingSpace;
